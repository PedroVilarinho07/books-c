package pt.psoft.g1.psoftg1.authormanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.psoft.g1.psoftg1.authormanagement.dto.response.AuthorCoAuthorBooksResponseDTO;
import pt.psoft.g1.psoftg1.authormanagement.dto.response.AuthorLendingResponseDTO;
import pt.psoft.g1.psoftg1.authormanagement.dto.response.AuthorResponseDTO;
import pt.psoft.g1.psoftg1.authormanagement.dto.response.CoAuthorResponseDTO;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;
import pt.psoft.g1.psoftg1.authormanagement.dto.request.CreateAuthorRequest;
import pt.psoft.g1.psoftg1.authormanagement.dto.request.UpdateAuthorRequest;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.response.BookResponseDTO;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookViewMapper;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.shared.api.ListResponse;
import pt.psoft.g1.psoftg1.shared.services.ConcurrencyService;
import pt.psoft.g1.psoftg1.shared.services.FileStorageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Tag(name = "Author", description = "Endpoints for managing Authors")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService AuthorService;
    private final AuthorAPIMapper authorAPIMapper;
    private final ConcurrencyService concurrencyService;
    private final FileStorageService fileStorageService;
    private final BookViewMapper bookViewMapper;


    //Create
    @Operation(summary = "Creates a new Author")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthorResponseDTO> create(@Valid CreateAuthorRequest resource) {
        //Guarantee that the client doesn't provide a link on the body, null = no photo or error
        resource.setPhotoURI(null);
        MultipartFile file = resource.getPhoto();

        String fileName = this.fileStorageService.getRequestPhoto(file);

        if (fileName != null) {
            resource.setPhotoURI(fileName);
        }

        final var author = AuthorService.create(resource);

        final var newauthorUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .build().toUri();

        return ResponseEntity.created(newauthorUri)
                .body(authorAPIMapper.toAuthorDTO(author));

    }

    //Update
    @Operation(summary = "Updates a specific author")
    @PatchMapping(value = "/{authorNumber}")
    public ResponseEntity<AuthorResponseDTO> partialUpdate(
            @PathVariable("authorNumber")
            @Parameter(description = "The number of the Author to find") final String authorNumber,
            final WebRequest request,
            @Valid UpdateAuthorRequest resource) {

        final String ifMatchValue = request.getHeader(ConcurrencyService.IF_MATCH);
        if (ifMatchValue == null || ifMatchValue.isEmpty() || ifMatchValue.equals("null")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must issue a conditional PATCH using 'if-match'");
        }

        MultipartFile file = resource.getPhoto();

        String fileName = this.fileStorageService.getRequestPhoto(file);

        if (fileName != null) {
            resource.setPhotoURI(fileName);
        }
        Author author = AuthorService.partialUpdate(authorNumber, resource);

        return ResponseEntity.ok()
                .body(authorAPIMapper.toAuthorDTO(author));
    }


    //Delete a foto
    @Operation(summary = "Deletes a author photo")
    @DeleteMapping("/{authorNumber}/photo")
    public ResponseEntity<Void> deleteBookPhoto(@PathVariable("authorNumber") final String authorNumber) {

        Optional<Author> optionalAuthor = AuthorService.findByAuthorNumber(authorNumber);
        if(optionalAuthor.isEmpty()) {
            throw new AccessDeniedException("A author could not be found with provided authorNumber");
        }
        Author author = optionalAuthor.get();
        if(author.getPhoto() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        this.fileStorageService.deleteFile(author.getPhoto().getPhotoFile());
        AuthorService.removeAuthorPhoto(author.getAuthorNumber().toString());

        return ResponseEntity.ok().build();
    }
}

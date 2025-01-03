package pt.psoft.g1.psoftg1.genremanagement.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.genremanagement.dto.response.GenreLendingsResponseDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.response.GenreBookCountResponseDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.response.GenreLendingsAvgPerMonthResponseDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.response.GenreLendingsCountPerMonthResponseDTO;
import pt.psoft.g1.psoftg1.genremanagement.mappers.GenreMapper;
import pt.psoft.g1.psoftg1.genremanagement.services.IGenreService;
import pt.psoft.g1.psoftg1.genremanagement.dto.request.GetAverageLendingsQuery;
import pt.psoft.g1.psoftg1.shared.api.ListResponse;
import pt.psoft.g1.psoftg1.shared.services.SearchRequest;

@Tag(name = "Genres", description = "Endpoints for managing Genres")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

    private final IGenreService IGenreService;
    private final GenreMapper genreMapper;


}

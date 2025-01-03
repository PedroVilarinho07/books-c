package pt.psoft.g1.psoftg1.authormanagement.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pt.psoft.g1.psoftg1.authormanagement.dto.response.AuthorLendingResponseDTO;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> findByAuthorNumber(String authorNumber);

    List<Author> searchByNameName(String name);
    Author save(Author author);
    Iterable<Author> findAll();

    void delete(Author author);


}

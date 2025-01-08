package pt.psoft.g1.psoftg1.genremanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.genremanagement.dto.GenreLendingsDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.GenreLendingsPerMonthDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.request.GetAverageLendingsQuery;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService implements IGenreService {

    private final GenreRepository GenreRepository;


    @Override
    public Iterable<Genre> findAll() {
        return GenreRepository.findAll();
    }



    @Override
    public Genre save(Genre genre) {
        return this.GenreRepository.save(genre);
    }


}

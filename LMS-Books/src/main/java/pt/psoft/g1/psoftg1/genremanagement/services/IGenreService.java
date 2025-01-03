package pt.psoft.g1.psoftg1.genremanagement.services;

import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.GenreLendingsDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.GenreLendingsPerMonthDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.request.GetAverageLendingsQuery;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.util.List;
import java.util.Optional;

public interface IGenreService {
    Iterable<Genre> findAll();
    Genre save(Genre genre);

}

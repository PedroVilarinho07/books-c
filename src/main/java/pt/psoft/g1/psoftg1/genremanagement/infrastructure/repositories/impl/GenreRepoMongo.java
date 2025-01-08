package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.bootstrapping.Entities;
import pt.psoft.g1.psoftg1.genremanagement.dto.GenreLendingsDTO;
import pt.psoft.g1.psoftg1.genremanagement.dto.GenreLendingsPerMonthDTO;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
public class GenreRepoMongo implements GenreRepository {

    private final List<Genre> staticGenres;

    public GenreRepoMongo() {
        staticGenres = new ArrayList<>();
        staticGenres.add(new Genre("Fantasia"));
        staticGenres.add(new Genre("Informação"));
        staticGenres.add(new Genre("Romance"));
        staticGenres.add(new Genre("Infantil"));
        staticGenres.add(new Genre("Thriller"));
    }

    @Override
    public Iterable<Genre> findAll() {
        return List.copyOf(staticGenres);
    }

    @Override
    public Optional<Genre> findByString(String genreName) {
        return staticGenres.stream()
                .filter(genre -> genre.toString().equalsIgnoreCase(genreName)) // Usando toString() como identificador do nome
                .findFirst();
    }

    @Override
    public Genre save(Genre genre) {
        staticGenres.add(genre);
        return genre;
    }



    @Override
    public List<Genre> getGenreWithMostLendings(int numberOfGenres) {
        return new ArrayList<>(Entities.getGenres().subList(0, numberOfGenres));
    }

    @Override
    public void delete(Genre genre) {
        staticGenres.removeIf(g -> g.toString().equalsIgnoreCase(genre.toString())); // Usando toString() como identificador do nome
    }
}

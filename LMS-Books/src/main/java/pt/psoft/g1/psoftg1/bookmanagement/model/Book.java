package pt.psoft.g1.psoftg1.bookmanagement.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.UpdateBookRequest;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.shared.model.EntityWithPhoto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book extends EntityWithPhoto {

    Isbn isbn;

    @NotNull
    Title title;

    @Getter
    @NotNull
    Genre genre;

    @Getter
    private List<Author> authors = new ArrayList<>();

    @Embedded
    Description description;

    private void setTitle(String title) {
        this.title = new Title(title);  // Cria um novo objeto Title
    }

    private void setIsbn(String isbn) {
        this.isbn = new Isbn(isbn);
    }

    private void setDescription(String description) {
        this.description = new Description(description);  // Cria um novo objeto Description
    }

    private void setGenre(Genre genre) {
        this.genre = genre;
    }

    private void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return this.description != null ? this.description.toString() : null;
    }

    public String getTitle() {
        return this.title != null ? this.title.toString() : null;
    }

    public Book(String isbn, String title, String description, Genre genre, List<Author> authors, String photoURI) {
        setTitle(title);
        setIsbn(isbn);
        if(description != null)
            setDescription(description);
        if(genre == null)
            throw new IllegalArgumentException("Genre cannot be null");
        setGenre(genre);
        if(authors == null)
            throw new IllegalArgumentException("Author list is null");
        if(authors.isEmpty())
            throw new IllegalArgumentException("Author list is empty");

        setAuthors(authors);
        setPhotoInternal(photoURI);
    }

    protected Book() {
        // For ORM only
    }
    public void removePhoto(long desiredVersion) {
        setPhotoInternal(null);  // Define a foto como nula, removendo a referência.
    }

    public void applyPatch(UpdateBookRequest request) {
        if (request.getTitle() != null) {
            setTitle(request.getTitle());  // Atualiza `title`
        }
        if (request.getDescription() != null) {
            setDescription(request.getDescription());  // Atualiza `description`
        }
        if (request.getGenreObj() != null) {
            setGenre(request.getGenreObj());  // Atualiza `genre`
        }
        if (request.getAuthorObjList() != null) {
            setAuthors(request.getAuthorObjList());  // Atualiza `authors`
        }
        if (request.getPhotoURI() != null) {
            setPhotoInternal(request.getPhotoURI());  // Atualiza `photoURI`
        }
    }

    public void applyPatch(final String title,
                           final String description,
                           final String photoURI,
                           final Genre genre,
                           final List<Author> authors ) {

        if (title != null) {
            setTitle(title);
        }

        if (description != null) {
            setDescription(description);
        }

        if (genre != null) {
            setGenre(genre);
        }

        if (authors != null) {
            setAuthors(authors);
        }

        if (photoURI != null)
            setPhotoInternal(photoURI);

    }


    public String getIsbn() {
        return this.isbn.toString();
    }
}

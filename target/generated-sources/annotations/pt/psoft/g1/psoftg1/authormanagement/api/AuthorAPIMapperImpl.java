package pt.psoft.g1.psoftg1.authormanagement.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.authormanagement.dto.response.AuthorCoAuthorBooksResponseDTO;
import pt.psoft.g1.psoftg1.authormanagement.dto.response.AuthorResponseDTO;
import pt.psoft.g1.psoftg1.authormanagement.dto.response.CoAuthorResponseDTO;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.response.BookShortResponseDTO;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-08T21:32:02+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (JetBrains s.r.o.)"
)
@Component
public class AuthorAPIMapperImpl extends AuthorAPIMapper {

    @Override
    public AuthorResponseDTO toAuthorDTO(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO();

        authorResponseDTO.setAuthorNumber( map( author.getAuthorNumber() ) );
        authorResponseDTO.setName( map( author.getName() ) );
        authorResponseDTO.setBio( map( author.getBio() ) );
        authorResponseDTO.setPhoto( map( author.getPhoto() ) );

        authorResponseDTO.set_links( mapLinks(author) );

        return authorResponseDTO;
    }

    @Override
    public List<AuthorResponseDTO> toAuthorDTO(List<Author> authors) {
        if ( authors == null ) {
            return null;
        }

        List<AuthorResponseDTO> list = new ArrayList<AuthorResponseDTO>( authors.size() );
        for ( Author author : authors ) {
            list.add( toAuthorDTO( author ) );
        }

        return list;
    }

    @Override
    public CoAuthorResponseDTO toCoAuthorDTO(Author author, List<Book> books) {
        if ( author == null && books == null ) {
            return null;
        }

        Map<String, Object> links = null;
        String name = null;
        if ( author != null ) {
            links = mapLinks( author );
            name = map( author.getName() );
        }
        List<BookShortResponseDTO> books1 = null;
        books1 = toBookShortDTO( books );

        CoAuthorResponseDTO coAuthorResponseDTO = new CoAuthorResponseDTO( name, links, books1 );

        return coAuthorResponseDTO;
    }

    @Override
    public BookShortResponseDTO toBookShortDTO(Book book) {
        if ( book == null ) {
            return null;
        }

        BookShortResponseDTO bookShortResponseDTO = new BookShortResponseDTO();

        bookShortResponseDTO.set_links( mapShortBookLink( book ) );
        bookShortResponseDTO.setTitle( map( book.getTitle() ) );
        bookShortResponseDTO.setIsbn( map( book.getIsbn() ) );

        return bookShortResponseDTO;
    }

    @Override
    public List<BookShortResponseDTO> toBookShortDTO(List<Book> books) {
        if ( books == null ) {
            return null;
        }

        List<BookShortResponseDTO> list = new ArrayList<BookShortResponseDTO>( books.size() );
        for ( Book book : books ) {
            list.add( toBookShortDTO( book ) );
        }

        return list;
    }

    @Override
    public AuthorCoAuthorBooksResponseDTO toAuthorCoAuthorBooksDTO(Author author, List<CoAuthorResponseDTO> coauthors) {
        if ( author == null && coauthors == null ) {
            return null;
        }

        AuthorResponseDTO author1 = null;
        author1 = toAuthorDTO( author );
        List<CoAuthorResponseDTO> coauthors1 = null;
        List<CoAuthorResponseDTO> list = coauthors;
        if ( list != null ) {
            coauthors1 = new ArrayList<CoAuthorResponseDTO>( list );
        }

        AuthorCoAuthorBooksResponseDTO authorCoAuthorBooksResponseDTO = new AuthorCoAuthorBooksResponseDTO( author1, coauthors1 );

        return authorCoAuthorBooksResponseDTO;
    }
}

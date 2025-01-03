package pt.psoft.g1.psoftg1.bookmanagement.api;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.bookmanagement.dto.amkp.BookDTOAmqp;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-03T19:40:59+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (JetBrains s.r.o.)"
)
@Component
public class BookAmqpMapperImpl extends BookAmqpMapper {

    @Override
    public BookDTOAmqp toBookViewAMQP(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDTOAmqp bookDTOAmqp = new BookDTOAmqp();

        bookDTOAmqp.setIsbn( map( book.getIsbn() ) );
        bookDTOAmqp.setDescription( map( book.getDescription() ) );
        bookDTOAmqp.setTitle( map( book.getTitle() ) );
        bookDTOAmqp.setGenre( map( book.getGenre() ) );

        bookDTOAmqp.setAuthorIds( mapAuthors(book.getAuthors()) );

        return bookDTOAmqp;
    }

    @Override
    public List<BookDTOAmqp> toBookViewAMQP(List<Book> bookList) {
        if ( bookList == null ) {
            return null;
        }

        List<BookDTOAmqp> list = new ArrayList<BookDTOAmqp>( bookList.size() );
        for ( Book book : bookList ) {
            list.add( toBookViewAMQP( book ) );
        }

        return list;
    }
}

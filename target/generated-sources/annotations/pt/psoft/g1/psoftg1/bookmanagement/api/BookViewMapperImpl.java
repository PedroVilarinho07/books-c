package pt.psoft.g1.psoftg1.bookmanagement.api;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.response.BookAverageLendingDurationResponseDTO;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.response.BookCountResponseDTO;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.response.BookResponseDTO;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-08T21:32:02+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (JetBrains s.r.o.)"
)
@Component
public class BookViewMapperImpl extends BookViewMapper {

    @Override
    public BookResponseDTO toBookView(Book book) {
        if ( book == null ) {
            return null;
        }

        BookResponseDTO bookResponseDTO = new BookResponseDTO();

        bookResponseDTO.setGenre( map( book.getGenre() ) );
        bookResponseDTO.setIsbn( map( book.getIsbn() ) );
        bookResponseDTO.setDescription( map( book.getDescription() ) );
        bookResponseDTO.setTitle( map( book.getTitle() ) );

        bookResponseDTO.setAuthors( mapAuthors(book.getAuthors()) );
        bookResponseDTO.set_links( mapLinks(book) );

        return bookResponseDTO;
    }

    @Override
    public List<BookResponseDTO> toBookView(List<Book> bookList) {
        if ( bookList == null ) {
            return null;
        }

        List<BookResponseDTO> list = new ArrayList<BookResponseDTO>( bookList.size() );
        for ( Book book : bookList ) {
            list.add( toBookView( book ) );
        }

        return list;
    }

    @Override
    public List<BookCountResponseDTO> toBookCountView(List<BookCountDTO> bookCountDto) {
        if ( bookCountDto == null ) {
            return null;
        }

        List<BookCountResponseDTO> list = new ArrayList<BookCountResponseDTO>( bookCountDto.size() );
        for ( BookCountDTO bookCountDTO : bookCountDto ) {
            list.add( bookCountDTOToBookCountResponseDTO( bookCountDTO ) );
        }

        return list;
    }

    @Override
    public BookAverageLendingDurationResponseDTO toBookAverageLendingDurationView(Book book, Double averageLendingDuration) {
        if ( book == null && averageLendingDuration == null ) {
            return null;
        }

        BookAverageLendingDurationResponseDTO bookAverageLendingDurationResponseDTO = new BookAverageLendingDurationResponseDTO();

        bookAverageLendingDurationResponseDTO.setBook( toBookView( book ) );
        bookAverageLendingDurationResponseDTO.setAverageLendingDuration( averageLendingDuration );

        return bookAverageLendingDurationResponseDTO;
    }

    protected BookCountResponseDTO bookCountDTOToBookCountResponseDTO(BookCountDTO bookCountDTO) {
        if ( bookCountDTO == null ) {
            return null;
        }

        BookCountResponseDTO bookCountResponseDTO = new BookCountResponseDTO();

        bookCountResponseDTO.setLendingCount( bookCountDTO.getLendingCount() );

        return bookCountResponseDTO;
    }
}

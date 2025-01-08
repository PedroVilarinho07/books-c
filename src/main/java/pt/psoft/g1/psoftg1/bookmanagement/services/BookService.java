package pt.psoft.g1.psoftg1.bookmanagement.services;


import pt.psoft.g1.psoftg1.bookmanagement.dto.amkp.BookDTOAmqp;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.CreateBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.SearchBooksQuery;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.UpdateBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.dto.request.RecommendationBooksRequestDTO;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.util.List;

/**
 *
 */
public interface BookService {
    Book create(CreateBookRequest request, String isbn);
    Book save(Book book);
    Book findByIsbn(String isbn);
    Book update(UpdateBookRequest request);

    //AMQP
    Book create(BookDTOAmqp bookDTOAmqp); // AMQP request
    Book update(BookDTOAmqp bookDTOAmqp);
}

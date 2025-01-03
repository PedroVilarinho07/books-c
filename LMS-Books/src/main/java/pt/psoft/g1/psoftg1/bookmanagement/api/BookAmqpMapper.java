package pt.psoft.g1.psoftg1.bookmanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.dto.amkp.BookDTOAmqp;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class BookAmqpMapper extends MapperInterface {

    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "genre", source = "genre")
    @Mapping(target = "authorIds", expression = "java(mapAuthors(book.getAuthors()))")

    public abstract BookDTOAmqp toBookViewAMQP(Book book);

    public abstract List<BookDTOAmqp> toBookViewAMQP(List<Book> bookList);

    protected List<String> mapAuthors(List<Author> authors) {
        return authors.stream()
                .map(author -> author.getAuthorNumber().getAuthorNumber()) // Example method
                .collect(Collectors.toList());
    }

}

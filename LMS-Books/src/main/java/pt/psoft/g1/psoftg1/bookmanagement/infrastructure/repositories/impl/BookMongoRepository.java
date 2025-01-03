package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.authormanagement.factories.AuthorFactory;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.SearchBooksQuery;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Profile("mongo")
public class BookMongoRepository implements BookRepository {

    private final AuthorFactory authorFactory;
    private final List<Book> staticBooks = new ArrayList<>();

    public BookMongoRepository(AuthorFactory authorFactory) {
        this.authorFactory = authorFactory;

        // Utilizando o AuthorFactory para criar instâncias completas de Author
        staticBooks.add(new Book("9789720706386", "O País das Pessoas de Pernas Para o Ar",
                "Histórias divertidas e com múltiplos significados",
                new Genre("Infantil"),
                List.of(authorFactory.newAuthor("Manuel Antonio Pina",
                        "Manuel António Pina foi um jornalista e escritor português, premiado em 2011 com o Prémio Camões", null)),
                null));

        staticBooks.add(new Book("9789723716160", "Como se Desenha Uma Casa",
                "Poesia e reflexão sobre a vida",
                new Genre("Infantil"),
                List.of(authorFactory.newAuthor("Manuel Antonio Pina",
                        "Manuel António Pina foi um jornalista e escritor português, premiado em 2011 com o Prémio Camões", null)),
                null));

        staticBooks.add(new Book("9789895612864", "C e Algoritmos",
                "Linguagem C e estruturas de dados",
                new Genre("Informação"),
                List.of(authorFactory.newAuthor("Alexandre Pereira",
                        "Alexandre Pereira é licenciado e mestre em Engenharia Electrotécnica e de Computadores...", null)),
                null));

        staticBooks.add(new Book("9782722203402", "Introdução ao Desenvolvimento Moderno para a Web",
                "Desenvolvimento moderno de aplicações Web",
                new Genre("Informação"),
                List.of(
                        authorFactory.newAuthor("Filipe Portela",
                                "Docente convidado na Escola de Engenharia da Universidade do Minho. Investigador integrado do Centro Algoritmi...", null),
                        authorFactory.newAuthor("Ricardo Queirós",
                                "Docente na Escola Superior de Media Artes e Design do Politécnico do Porto...", null)
                ),
                null));
    }



    @Override
    public List<Book> findXBooksByGenre(String genre, Integer numberOfBooks) {
        return staticBooks.stream()
                .filter(book -> book.getGenre().toString().equalsIgnoreCase(genre))
                .limit(numberOfBooks)
                .collect(Collectors.toList());
    }



    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return staticBooks.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();
    }



    @Override
    public Book save(Book book) {
        staticBooks.add(book);
        return book;
    }

    @Override
    public void delete(Book book) {
        staticBooks.removeIf(b -> b.getIsbn().equals(book.getIsbn()));
    }





    // Método auxiliar para identificar o gênero mais emprestado de um leitor específico
    public String getMostLentGenreByReader(String readerNumber) {
        // Simula a contagem de empréstimos por gênero usando streams e coletores
        return staticBooks.stream()
                .filter(book -> book.getGenre() != null && book.getAuthors() != null)
                .flatMap(book -> book.getAuthors().stream()
                        .filter(author -> author.getAuthorNumber().equals(readerNumber))
                        .map(author -> book.getGenre().toString()))
                .collect(Collectors.groupingBy(g -> g, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

}

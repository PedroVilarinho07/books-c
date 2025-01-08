package pt.psoft.g1.psoftg1.bookmanagement.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.dto.amkp.BookDTOAmqp;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.CreateBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.SearchBooksQuery;
import pt.psoft.g1.psoftg1.bookmanagement.dto.rest.request.UpdateBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.model.*;
import pt.psoft.g1.psoftg1.bookmanagement.model.domainService.BookRecommendation;
import pt.psoft.g1.psoftg1.bookmanagement.publishers.BookEventsPublisher;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.genremanagement.dto.request.RecommendationBooksRequestDTO;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@PropertySource({"classpath:config/library.properties"})
public class BookServiceImpl implements BookService {

	private final BookEventsPublisher bookEventsPublisher;

	private final BookRepository bookRepository;
	private final GenreRepository GenreRepository;
	private final AuthorRepository AuthorRepository;
	private final PhotoRepository photoRepository;
	private final BookRecommendation bookRecommendation;

	@Value("${suggestionsLimitPerGenre}")
	private long suggestionsLimitPerGenre;

	@Override
	public Book create(CreateBookRequest request, String isbn) {

		final String title = request.getTitle();
		final String description = request.getDescription();
		final String photoURI = request.getPhotoURI();
		final String genre = request.getGenre();
		final List<String> authorIds = request.getAuthors();

		Book savedBook = create(isbn, title, description, photoURI, genre, authorIds);

		if( savedBook!=null ) {
			bookEventsPublisher.sendBookCreated(savedBook);
		}

		return savedBook;
	}

	@Override
	public Book create(BookDTOAmqp bookViewAMQP) {

		final String isbn = bookViewAMQP.getIsbn();
		final String description = bookViewAMQP.getDescription();
		final String title = bookViewAMQP.getTitle();
		final String photoURI = null;
		final String genre = bookViewAMQP.getGenre();
		final List<String> authorIds = bookViewAMQP.getAuthorIds();

		Book bookCreated = create(isbn, title, description, photoURI, genre, authorIds);

		return bookCreated;
	}

	private Book create( String isbn,
						 String title,
						 String description,
						 String photoURI,
						 String genreName,
						 List<String> authorIds) {

		if (bookRepository.findByIsbn(isbn).isPresent()) {
			throw new ConflictException("Book with ISBN " + isbn + " already exists");
		}

		List<Author> authors = getAuthors(authorIds);

		final Genre genre = GenreRepository.findByString(String.valueOf(genreName))
				.orElseThrow(() -> new NotFoundException("Genre not found"));

		Book newBook = new Book(isbn, title, description, genre, authors, photoURI);

		Book savedBook = bookRepository.save(newBook);

		return savedBook;
	}

	@Override
	public Book update(UpdateBookRequest request) {
		var book = findByIsbn(request.getIsbn());

		List<String> authorsId = request.getAuthors();

		MultipartFile photo = request.getPhoto();
		String photoURI = request.getPhotoURI();
		if (photo == null && photoURI != null || photo != null && photoURI == null) {
			photoURI = null;
		}

		String genreId = request.getGenre();
		String title = request.getTitle();
		String description = request.getDescription();

		Book updatedBook = update( book, title, description, photoURI, genreId, authorsId);
		if( updatedBook!=null ) {
			bookEventsPublisher.sendBookUpdated(updatedBook);
		}

		return updatedBook;
	}

	@Override
	public Book update(BookDTOAmqp bookViewAMQP) {
		final String isbn = bookViewAMQP.getIsbn();
		final String description = bookViewAMQP.getDescription();
		final String title = bookViewAMQP.getTitle();
		final String photoURI = null;
		final String genre = bookViewAMQP.getGenre();
		final List<String> authorIds = bookViewAMQP.getAuthorIds();

		var book = findByIsbn(isbn);

		Book bookUpdated = update(book, title, description, photoURI, genre, authorIds);

		return bookUpdated;
	}

	private Book update( Book book,
						 String title,
						 String description,
						 String photoURI,
						 String genreId,
						 List<String> authorsId) {

		Genre genreObj = null;
		if (genreId != null) {
			Optional<Genre> genre = GenreRepository.findByString(genreId);
			if (genre.isEmpty()) {
				throw new NotFoundException("Genre not found");
			}
			genreObj = genre.get();
		}

		List<Author> authors = new ArrayList<>();
		if (authorsId != null) {
			for (String authorNumber : authorsId) {
				Optional<Author> temp = AuthorRepository.findByAuthorNumber(authorNumber);
				if (temp.isEmpty()) {
					continue;
				}
				Author author = temp.get();
				authors.add(author);
			}
		}
		else
			authors = null;

		book.applyPatch(title, description, photoURI, genreObj, authors);

		Book updatedBook = bookRepository.save(book);

		return updatedBook;
	}

	@Override
	public Book save(Book book) {
		return this.bookRepository.save(book);
	}


	public Book findByIsbn(String isbn) {
		return this.bookRepository.findByIsbn(isbn)
				.orElseThrow(() -> new NotFoundException(Book.class, isbn));
	}

	public List<Book> getBooksSuggestionsForReader(RecommendationBooksRequestDTO request) {
		return bookRecommendation.bookRecommendations(request);
	}

	/*public List<Book> getBooksSuggestionsForReader(String readerNumber) {
		List<Book> books = new ArrayList<>();

		ReaderDetails readerDetails = readerRepository.findByReaderNumber(readerNumber)
				.orElseThrow(() -> new NotFoundException("Reader not found with provided login"));
		List<Genre> interestList = readerDetails.getInterestList();

		if(interestList.isEmpty()) {
			throw new NotFoundException("Reader has no interests");
		}

		for(Genre genre : interestList) {
			List<Book> tempBooks = bookRepository.findByGenre(genre.toString());
			if(tempBooks.isEmpty()) {
				continue;
			}

			long genreBookCount = 0;

            for (Book loopBook : tempBooks) {
                if (genreBookCount >= suggestionsLimitPerGenre) {
                    break;
                }

                books.add(loopBook);
				genreBookCount++;
            }
		}

		return books;
	}

	 */


	private List<Author> getAuthors(List<String> authorNumbers) {

		List<Author> authors = new ArrayList<>();
		for (String authorNumber : authorNumbers) {

			Optional<Author> temp = AuthorRepository.findByAuthorNumber(authorNumber);
			if (temp.isEmpty()) {
				continue;
			}

			Author author = temp.get();
			authors.add(author);
		}

		return authors;
	}
}

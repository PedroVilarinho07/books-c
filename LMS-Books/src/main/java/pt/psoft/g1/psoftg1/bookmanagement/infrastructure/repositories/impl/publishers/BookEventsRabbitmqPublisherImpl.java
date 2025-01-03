package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.publishers;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookAmqpMapper;
import pt.psoft.g1.psoftg1.bookmanagement.dto.amkp.BookDTOAmqp;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.publishers.BookEventsPublisher;

import pt.psoft.g1.psoftg1.shared.model.events.BookEvents;

@Service
@RequiredArgsConstructor
public class BookEventsRabbitmqPublisherImpl implements BookEventsPublisher {

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private DirectExchange direct;
    private final BookAmqpMapper bookViewAMQPMapper;

    private int count = 0;

    @Override
    public void sendBookCreated(Book book) {
        sendBookEvent(book, BookEvents.BOOK_CREATED);
    }

    @Override
    public void sendBookUpdated(Book book) {
        sendBookEvent(book, BookEvents.BOOK_UPDATED);
    }

    @Override
    public void sendBookDeleted(Book book) {
        sendBookEvent(book, BookEvents.BOOK_DELETED);
    }

    public void sendBookEvent(Book book, String bookEventType) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            BookDTOAmqp bookViewAMQP = bookViewAMQPMapper.toBookViewAMQP(book);

            String jsonString = objectMapper.writeValueAsString(bookViewAMQP);

            this.template.convertAndSend(direct.getName(), bookEventType, jsonString);

            System.out.println(" [x] Sent '" + jsonString + "'");
        }
        catch( Exception ex ) {
            System.out.println(" [x] Exception sending book event: '" + ex.getMessage() + "'");
        }
    }
}

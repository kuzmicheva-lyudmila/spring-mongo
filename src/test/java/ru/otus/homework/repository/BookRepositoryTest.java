package ru.otus.homework.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.homework.model.Book;

import static org.junit.Assert.assertNotNull;

@DataMongoTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
    public void shouldSetIdOnSave() {
        Mono<Book> bookMono = repository.save(new Book());

        StepVerifier
                .create(bookMono)
                .assertNext(book -> assertNotNull(book.getId()))
                .expectComplete()
                .verify();
    }
}

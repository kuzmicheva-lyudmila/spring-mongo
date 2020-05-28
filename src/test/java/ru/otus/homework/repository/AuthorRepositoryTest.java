package ru.otus.homework.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;
import ru.otus.homework.model.Author;

@DataMongoTest
public class AuthorRepositoryTest {

    public static final String AUTHOR = "author";

    @Autowired
    private AuthorRepository repository;

    @BeforeEach
    public void beforeAll() {
        repository.save(new Author(null, AUTHOR));
    }

    @Test
    public void shouldFindByFullname() {
        StepVerifier.create(repository.findFirstByFullName(AUTHOR))
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }
}

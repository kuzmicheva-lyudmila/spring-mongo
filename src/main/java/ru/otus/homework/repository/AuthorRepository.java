package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.otus.homework.model.Author;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

    Mono<Author> findFirstByFullName(String fullName);
}

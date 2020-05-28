package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.homework.model.Book;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}

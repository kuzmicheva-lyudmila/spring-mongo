package ru.otus.homework.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.controller.dto.BookDto;
import ru.otus.homework.model.Book;

public interface BookInfoService {
    Mono<Book> insertBook(final BookDto bookDto);
    Mono<Book> insertBook(final Book book);
    Mono<Book> updateTitleBookById(final String bookId, final String newBookTitle);
    Mono<Void> deleteBookById(final String bookId);
    Flux<Book> getAllBooks();
    Mono<Book> getBookById(final String bookId);
}

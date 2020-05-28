package ru.otus.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.controller.dto.BookDto;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service("bookInfoServiceImpl")
@Slf4j
public class BookInfoServiceImpl implements BookInfoService {
    private static final String ERROR_ON_UPDATING_BOOK = "error on updating book";
    private static final String ERROR_ON_INSERTING_BOOK = "error on inserting book";
    private static final String ERROR_ON_DELETING_BOOK = "error on deleting book";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookInfoServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Mono<Book> getBookById(String bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public Mono<Book> insertBook(final BookDto bookDto) {
        final Book book = Book.builder()
                .fullName(bookDto.getFullname())
                .genre(bookDto.getGenre())
                .description(bookDto.getDescription())
                .build();
        return Flux.fromStream(formAuthorList(bookDto.getAuthors()).stream())
                .flatMap(this::buildAuthor)
                .map(Author::getId)
                .collectList()
                .flatMap(
                        authorIds -> {
                            book.setAuthorsIds(authorIds);
                            return bookRepository.save(book);
                        }
                )
                .doOnError(error -> log.error(ERROR_ON_INSERTING_BOOK, error));
    }

    @Override
    public Mono<Book> insertBook(final Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Mono<Book> updateTitleBookById(String bookId, String newBookTitle) {
        return getBookById(bookId).switchIfEmpty(Mono.empty())
                .filter(Objects::nonNull)
                .flatMap(
                        book -> {
                            book.setFullName(newBookTitle);
                            return bookRepository.save(book);
                        }
                )
                .doOnError(error -> log.error(ERROR_ON_UPDATING_BOOK, error));
    }

    @Override
    public Mono<Void> deleteBookById(String bookId) {
        return getBookById(bookId).switchIfEmpty(Mono.empty())
                .filter(Objects::nonNull)
                .flatMap(bookRepository::delete)
                .doOnError(error -> log.error(ERROR_ON_DELETING_BOOK, error));
    }

    @Override
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll().switchIfEmpty(Flux.empty());
    }

    private List<Author> formAuthorList(String authorFullNames) {
        List<Author> userAuthorList = new ArrayList<>();
        if (authorFullNames != null) {
            Arrays.stream(authorFullNames.split(";"))
                    .forEach(fullName -> userAuthorList.add(new Author(null, fullName)));
        }
        return userAuthorList;
    }

    private Mono<Author> buildAuthor(Author author) {
        return authorRepository.findFirstByFullName(author.getFullName())
                .switchIfEmpty(authorRepository.save(author));
    }
}

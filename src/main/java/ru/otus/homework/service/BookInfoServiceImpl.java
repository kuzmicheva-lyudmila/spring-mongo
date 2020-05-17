package ru.otus.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.BookRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookInfoServiceImpl implements BookInfoService {
    private static final String ERROR_ON_UPDATING_BOOK = "error on updating book";
    private static final String ERROR_ON_INSERTING_BOOK = "error on inserting book";
    private static final String ERROR_ON_DELETING_BOOK = "error on deleting book";

    private final BookRepository bookRepository;

    public BookInfoServiceImpl(
            BookRepository bookRepository
    ) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book insertBook(String title, String authors, String genre, String description) {
        List<Author> authorList = formAuthorList(authors);
        try {
            Book newBook = new Book(null, title, Genre.valueOf(genre), authorList, description, null);
            return bookRepository.save(newBook);
        } catch (Exception e) {
            log.error(ERROR_ON_INSERTING_BOOK, e);
        }
        return null;
    }

    @Override
    public Book insertBook(Book book) {
        if (book != null) {
            try {
                return bookRepository.save(book);
            } catch (Exception e) {
                log.error(ERROR_ON_INSERTING_BOOK, e);
            }
        }

        return null;
    }

    @Override
    public Book updateTitleBookById(String bookId, String newBookTitle) {
        Book updatedBook = getBookById(bookId);
        if (updatedBook != null) {
            updatedBook.setFullName(newBookTitle);
            return updateBook(updatedBook);
        }
        return null;
    }

    @Override
    public boolean deleteBookById(String bookId) {
        try {
            Book book = getBookById(bookId);
            if (book != null) {
                bookRepository.delete(book);
                return true;
            }
        } catch (Exception e) {
            log.error(ERROR_ON_DELETING_BOOK, e);
        }
        return false;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(String bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        return optionalBook.orElse(null);
    }

    private List<Author> formAuthorList(String authorFullNames) {
        List<Author> userAuthorList = new ArrayList<>();
        if (authorFullNames != null) {
            Arrays.stream(authorFullNames.split(";"))
                    .forEach(fullName -> userAuthorList.add(new Author(null, fullName, null)));
        }
        return userAuthorList;
    }

    private Book updateBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (Exception e) {
            log.error(ERROR_ON_UPDATING_BOOK, e);
        }
        return null;
    }
}

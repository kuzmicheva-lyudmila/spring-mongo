package ru.otus.homework.service;

import ru.otus.homework.model.Book;

import java.util.List;

public interface BookInfoService {
    Book insertBook(String title, String authors, String genre, String description);
    Book insertBook(Book book);
    Book updateTitleBookById(String bookId, String newBookTitle);
    boolean deleteBookById(String bookId);
    List<Book> getAllBooks();
    Book getBookById(String bookId);
}

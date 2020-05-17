package ru.otus.homework.repository;

import ru.otus.homework.model.Author;

import java.util.List;

public interface BookRepositoryCustom {

    List<Author> getBookAuthorsById(String bookId);
}

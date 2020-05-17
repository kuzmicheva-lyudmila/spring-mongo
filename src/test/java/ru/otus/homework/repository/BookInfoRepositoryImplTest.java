package ru.otus.homework.repository;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties
@ComponentScan({"ru.otus.homework.config", "ru.otus.homework.repository", "ru.otus.homework.event"})
public class BookInfoRepositoryImplTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void shouldCorrectSaveBookWithNewAuthor(){
        val book = new Book(
                null,
                "secondBook",
                Genre.HISTORY,
                List.of(new Author(null, "thirdAuthor", null)),
                "description",
                null
        );
        val actual = bookRepository.save(book);
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getFullName()).isEqualTo(book.getFullName());
    }

    @Test
    void shouldReturnCorrectBookAuthorList(){
        val book = bookRepository.findAll().get(0);
        List<Author> authorList = bookRepository.getBookAuthorsById(book.getId());
        assertThat(authorList).containsExactlyInAnyOrderElementsOf(book.getAuthors());
    }

    @Test
    void shouldDeleteAuthorIfNotContainInBook() {
        val book = bookRepository.findAll().get(0);
        val author = book.getAuthors().get(0);
        assertThatThrownBy(() -> authorRepository.delete(author)).isInstanceOf(IllegalArgumentException.class);
    }
}

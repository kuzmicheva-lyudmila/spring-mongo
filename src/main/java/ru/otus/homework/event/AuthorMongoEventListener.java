package ru.otus.homework.event;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework.model.Author;
import ru.otus.homework.repository.BookRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorMongoEventListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;

    @SneakyThrows
    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        String id = String.valueOf(event.getSource().get("_id"));
        Optional<Author> optionalAuthor = bookRepository.findAll()
                .stream()
                .flatMap(book -> book.getAuthors().stream())
                .filter(author -> author.getId().equals(id))
                .findFirst();
        if (optionalAuthor.isPresent()) {
            throw new IllegalArgumentException("Author uses in books");
        }
    }
}

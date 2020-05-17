package ru.otus.homework.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;

import java.util.List;

@ChangeLog(order = "001")
public class DatabaseChangeLog {

    private Author firstAuthor;
    private Author secondAuthor;

    @ChangeSet(order = "000", id = "dropDB", author = "kl")
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "kl")
    public void initAuthors(MongoTemplate template){
        firstAuthor = template.save(new Author(null, "firstAuthor", null));
        secondAuthor = template.save(new Author(null, "secondAuthor", null));
    }

    @ChangeSet(order = "002", id = "initBooks", author = "kl")
    public void initBooks(MongoTemplate template){
        Book book = new Book(
                null,
                "firstBook",
                Genre.HISTORY,
                List.of(firstAuthor, secondAuthor),
                "someDescription",
                null
        );
        template.save(book);
    }
}

package ru.otus.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.lookup;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import static org.springframework.data.mongodb.core.aggregation.ObjectOperators.ObjectToArray.valueOfToArray;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Author> getBookAuthorsById(String bookId) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("id").is(bookId))
                , unwind("authors")
                , project().andExclude("_id").and(valueOfToArray("authors")).as("author_map")
                , project().and("author_map").arrayElementAt(1).as("author_id_map")
                , project().and("author_id_map.v").as("author_id")
                , lookup("authors", "author_id", "_id", "author")
                , project().and("author._id").as("_id").and("author.full_name").as("full_name")
        );

        return mongoTemplate.aggregate(aggregation, Book.class, Author.class).getMappedResults();
    }
}

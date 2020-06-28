package ru.otus.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    private String id;

    @Field(value = "full_name")
    private String fullName;

    private Genre genre;

    @Field("authors")
    private List<String> authorsIds;

    @Transient
    @JsonIgnore
    private List<Author> authors;

    private String description;

    private List<Post> posts;

}

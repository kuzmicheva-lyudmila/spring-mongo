package ru.otus.homework.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework.model.Genre;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String id;
    private String fullname;
    private Genre genre;
    private String authors;
    private String description;
}

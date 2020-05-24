package ru.otus.homework.service;

import ru.otus.homework.model.Post;

import java.util.List;

public interface PostService {
    List<Post> insertPostByBook(String bookId, String description);
    boolean deletePostsByBook(String bookId);
    List<Post> getPostsByBook(String bookId);
}

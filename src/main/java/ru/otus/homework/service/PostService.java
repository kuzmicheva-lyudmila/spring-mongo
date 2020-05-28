package ru.otus.homework.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.model.Post;

public interface PostService {
    Flux<Post> insertPostByBook(String bookId, String description);
    Mono<Void> deletePostsByBook(String bookId);
    Flux<Post> getPostsByBook(String bookId);
}

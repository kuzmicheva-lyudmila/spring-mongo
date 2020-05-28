package ru.otus.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.model.Post;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    private static final String ERROR_ON_INSERTING_POST = "error on inserting post";

    private static final String ERROR_ON_DELETING_POST = "error on deleting post";

    private final BookInfoService bookInfoService;

    public PostServiceImpl(BookInfoService bookInfoService) {
        this.bookInfoService = bookInfoService;
    }

    @Override
    public Flux<Post> insertPostByBook(String bookId, String description) {
        return bookInfoService.getBookById(bookId).switchIfEmpty(Mono.empty())
                .filter(Objects::nonNull)
                .flatMap(
                        book -> {
                            if (book.getPosts() == null ) {
                                book.setPosts(List.of(new Post(description)));
                            } else {
                                List<Post> posts = book.getPosts();
                                posts.add(new Post(description));
                                book.setPosts(posts);
                            }
                            return bookInfoService.insertBook(book);
                        }
                )
                .doOnError(error -> log.error(ERROR_ON_INSERTING_POST, error))
                .flatMapMany(book -> Flux.fromStream(book.getPosts().stream()));

    }

    @Override
    public Mono<Void> deletePostsByBook(String bookId) {
        return bookInfoService.getBookById(bookId).switchIfEmpty(Mono.empty())
                .filter(Objects::nonNull)
                .flatMap(
                        book -> {
                            book.setPosts(null);
                            return bookInfoService.insertBook(book);
                        }
                ).then()
                .doOnError(error -> log.error(ERROR_ON_DELETING_POST, error));
    }

    @Override
    public Flux<Post> getPostsByBook(String bookId) {
        return bookInfoService.getBookById(bookId).switchIfEmpty(Mono.empty())
                .filter(Objects::nonNull)
                .flatMapMany(book -> Flux.fromStream(book.getPosts().stream()));
    }
}

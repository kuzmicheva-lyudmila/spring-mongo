package ru.otus.homework.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.homework.model.Post;
import ru.otus.homework.service.PostService;

@Component
public class PostHandler {

    private final PostService postService;

    public PostHandler(PostService postService) {
        this.postService = postService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        String id = request.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(postService.getPostsByBook(id), Post.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        String id = request.pathVariable("id");
        String description = request.queryParam("description").orElse("");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(postService.insertPostByBook(id, description), Post.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(postService.deletePostsByBook(id), Void.class);
    }
}

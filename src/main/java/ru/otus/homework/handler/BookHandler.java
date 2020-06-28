package ru.otus.homework.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.homework.controller.dto.BookDto;
import ru.otus.homework.model.Book;
import ru.otus.homework.service.BookInfoService;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class BookHandler {

    private final BookInfoService bookInfoService;

    public BookHandler(BookInfoService bookInfoService) {
        this.bookInfoService = bookInfoService;
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        final Mono<BookDto> book = request.bodyToMono(BookDto.class);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(book.flatMap(bookInfoService::insertBook), Book.class));
    }

    public Mono<ServerResponse> updateBook(ServerRequest request) {
        String id = request.pathVariable("id");
        String title = request.queryParam("title").orElse("");
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookInfoService.updateTitleBookById(id, title), Book.class);
    }

    public Mono<ServerResponse> deleteBook(ServerRequest request) {
        String id = request.pathVariable("id");
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookInfoService.deleteBookById(id), Void.class);
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookInfoService.getAllBooks(), Book.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookInfoService.getBookById(id), Book.class);
    }
}

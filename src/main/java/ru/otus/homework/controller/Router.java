package ru.otus.homework.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.homework.handler.BookHandler;
import ru.otus.homework.handler.PostHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class Router {

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(BookHandler bookHandler, PostHandler postHandler) {
        return RouterFunctions.route(POST("/books").and(accept(MediaType.APPLICATION_JSON)), bookHandler::save)
                .andRoute(PATCH("/books/{id}").and(accept(MediaType.APPLICATION_JSON)), bookHandler::updateBook)
                .andRoute(DELETE("/books/{id}").and(accept(MediaType.APPLICATION_JSON)), bookHandler::deleteBook)
                .andRoute(GET("/books").and(accept(MediaType.APPLICATION_JSON)), bookHandler::findAll)
                .andRoute(GET("/books/{id}").and(accept(MediaType.APPLICATION_JSON)), bookHandler::findById)
                .andRoute(GET("/books/{id}/posts").and(accept(MediaType.APPLICATION_JSON)), postHandler::findAll)
                .andRoute(POST("/books/{id}/posts").and(accept(MediaType.APPLICATION_JSON)), postHandler::save)
                .andRoute(DELETE("/books/{id}/posts").and(accept(MediaType.APPLICATION_JSON)), postHandler::delete);
    }
}

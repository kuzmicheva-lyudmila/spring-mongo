package ru.otus.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    private final BookInfoService bookInfoService;

    public PostServiceImpl(
            BookInfoService bookInfoService
    ) {
        this.bookInfoService = bookInfoService;
    }

    @Override
    public List<Post> insertPostByBook(String bookId, String description) {
        try {
            Book book = bookInfoService.getBookById(bookId);
            if (book != null) {
                List<Post> posts = book.getPosts();
                if (posts == null) {
                    posts = new ArrayList<>();
                }
                posts.add(new Post(description));
                book.setPosts(posts);
                bookInfoService.insertBook(book);
                return posts;
            }
        } catch (Exception e) {
            log.error("error on inserting post", e);
        }

        return Collections.emptyList();
    }

    @Override
    public boolean deletePostsByBook(String bookId) {
        try {
            Book book = bookInfoService.getBookById(bookId);
            if (book != null) {
                book.setPosts(null);
                bookInfoService.insertBook(book);
                return true;
            }
        } catch (Exception e) {
            log.error("error on deleting post", e);
        }

        return false;
    }

    @Override
    public List<Post> getPostsByBook(String bookId) {
        Book book = bookInfoService.getBookById(bookId);
        if (book != null) {
            return book.getPosts();
        } else {
            return Collections.emptyList();
        }
    }
}

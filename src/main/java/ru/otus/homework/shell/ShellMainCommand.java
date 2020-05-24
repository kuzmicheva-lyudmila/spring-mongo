package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;
import ru.otus.homework.model.Post;
import ru.otus.homework.service.BookInfoService;
import ru.otus.homework.service.CommunicationService;
import ru.otus.homework.service.PostService;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ShellComponent
public class ShellMainCommand {
    public static final String TEMPLATE = "[^.]+";
    public static final String ENTER_ID_OF_A_BOOK_MSG = "Enter ID of a book";
    public static final String INCORRECT_ID_TRY_AGAIN_MSG = "Incorrect ID! Try again";

    private final BookInfoService bookInfoService;
    private final PostService postService;
    private final CommunicationService communicationService;

    public ShellMainCommand(
            BookInfoService bookInfoService,
            PostService postService,
            CommunicationService communicationService
    ) {
        this.bookInfoService = bookInfoService;
        this.postService = postService;
        this.communicationService = communicationService;
    }

    @ShellMethod(value = "InsertBook", key = {"insertBook", "ib"})
    public void insertBook() throws UnsupportedEncodingException {
        String title = communicationService.getUserInputString(
                "Enter the name of a book",
                "Incorrect name! Enter a name again",
                TEMPLATE
        );

        String description = communicationService.getUserInputString(
                "Enter a book description",
                "Incorrect name! Enter a description again",
                TEMPLATE
        );

        String authors = communicationService.getUserInputString(
                "Enter book authors (delimiter ';')",
                "Incorrect list of authors! Enter book authors again",
                TEMPLATE
        );

        String genre = communicationService.getUserInputString(
                "Enter a book genre",
                "Incorrect genre! Enter a genre again",
                Stream.of(Genre.values()).map(Genre::name).collect(Collectors.toList())
        );

        Book book = bookInfoService.insertBook(title, authors, genre, description);
        communicationService.showResultMessage(
                book != null,
                "inserted: " + book,
                "error on inserting book"
        );
    }

    @ShellMethod(value = "UpdateBook", key = {"updateBook", "ub"})
    public void updateBook() throws UnsupportedEncodingException {
        String id = communicationService.getUserInputString(
                ENTER_ID_OF_A_BOOK_MSG,
                INCORRECT_ID_TRY_AGAIN_MSG,
                TEMPLATE
        );

        String title = communicationService.getUserInputString(
                "Enter a book name",
                "Incorrect name! Enter a name again",
                TEMPLATE
        );

        Book book = bookInfoService.updateTitleBookById(id, title);
        communicationService.showResultMessage(
                book != null,
                "updated book: " + book,
                "error on updating book"
        );
    }

    @ShellMethod(value = "DeleteBook", key = {"deleteBook", "db"})
    public void deleteBook() throws UnsupportedEncodingException {
        String id = communicationService.getUserInputString(
                ENTER_ID_OF_A_BOOK_MSG,
                INCORRECT_ID_TRY_AGAIN_MSG,
                TEMPLATE
        );

        boolean result = bookInfoService.deleteBookById(id);
        communicationService.showResultMessage(
                result,
                "deleted book with id = " + id,
                "error on updating book"
        );
    }

    @ShellMethod(value = "AllBooks", key = {"showBooks", "sb"})
    public void showBooks() throws UnsupportedEncodingException {
        List<Book> books = bookInfoService.getAllBooks();
        communicationService.showResultMessageList(books);
    }

    @ShellMethod(value = "SetPostOnBook", key = {"setPost", "sp"})
    public void setPost() throws UnsupportedEncodingException {
        String id = communicationService.getUserInputString(
                ENTER_ID_OF_A_BOOK_MSG,
                INCORRECT_ID_TRY_AGAIN_MSG,
                TEMPLATE
        );
        String description = communicationService.getUserInputString(
                "Enter a book post",
                "Incorrect post! Try again",
                TEMPLATE
        );

        List<Post> post = postService.insertPostByBook(id, description);
        communicationService.showResultMessage(
                post != null,
                "inserted post: " + post,
                "error on inserting post"
        );
    }

    @ShellMethod(value = "DeletePostsOnBook", key = {"deletePosts", "dp"})
    public void deletePosts() throws UnsupportedEncodingException {
        String id = communicationService.getUserInputString(
                ENTER_ID_OF_A_BOOK_MSG,
                INCORRECT_ID_TRY_AGAIN_MSG,
                TEMPLATE
        );
        boolean result = postService.deletePostsByBook(id);
        communicationService.showResultMessage(
                result,
                "deleted posts for book with id = " + id,
                "error on deleting posts"
        );
    }

    @ShellMethod(value = "GetPostsOnBook", key = {"getPosts", "gp"})
    public void getPosts() throws UnsupportedEncodingException {
        String id = communicationService.getUserInputString(
                ENTER_ID_OF_A_BOOK_MSG,
                INCORRECT_ID_TRY_AGAIN_MSG,
                TEMPLATE
        );
        List<Post> posts = postService.getPostsByBook(id);
        communicationService.showResultMessageList(posts);
    }
}

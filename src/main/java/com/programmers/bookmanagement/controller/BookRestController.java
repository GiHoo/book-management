package com.programmers.bookmanagement.controller;

import com.programmers.bookmanagement.dto.CreateBookRequest;
import com.programmers.bookmanagement.dto.UpdateBookRequest;
import com.programmers.bookmanagement.model.Book;
import com.programmers.bookmanagement.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/books")
    public List<Book> booksPage(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return books;
    }

    @PostMapping("/new-book")
    public Book newBook(@RequestBody CreateBookRequest createBookRequest) {
        return bookService.createBook(
                createBookRequest.bookName(),
                createBookRequest.category(),
                createBookRequest.price(),
                createBookRequest.description()
        );
    }

    @PutMapping("/books")
    public Book updateBook(@RequestBody UpdateBookRequest updateBookRequest) {
        Book book = new Book(
                UUID.fromString(updateBookRequest.bookId()),
                updateBookRequest.bookName(),
                updateBookRequest.category(),
                updateBookRequest.price(),
                updateBookRequest.description()
        );
        return bookService.update(book);
    }

    @DeleteMapping("/books/{bookId}")
    public Book deleteBook(@PathVariable String bookId) {
        return bookService.deleteById(UUID.fromString(bookId));
    }
}

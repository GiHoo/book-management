package com.programmers.bookmanagement.service;

import com.programmers.bookmanagement.model.Book;
import com.programmers.bookmanagement.model.Category;
import com.programmers.bookmanagement.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book createBook(String bookName, Category category, long price, String description) {
        Book book = new Book(UUID.randomUUID(), bookName, category, price, description);
        return bookRepository.insert(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book update(Book book) {
        Book beforeBook = findById(book.getBookId())
                .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));

        Book newBook = setUpdate(beforeBook, book);

        return bookRepository.update(newBook);
    }

    @Override
    public Optional<Book> findById(UUID bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public Optional<Book> findByName(String bookName) {
        return bookRepository.findByName(bookName);
    }

    @Override
    public Optional<Book> findByCategory(Category category) {
        return bookRepository.findByCategory(category);
    }

    @Override
    public Book deleteById(UUID bookId) {
        System.out.println(bookId);
        return bookRepository.deleteById(bookId);
    }

    private Book setUpdate(Book before, Book after) {
        String bookName;
        Category category;
        long price;
        String description;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;

        if (after.getBookName().equals("")) {
            bookName = before.getBookName();
        } else bookName = after.getBookName();

        if (after.getCategory().toString().equals("")) {
            category = before.getCategory();
        } else category = after.getCategory();

        if (after.getPrice() == 0) {
            price = before.getPrice();
        } else price = after.getPrice();

        if (before.getDescription().equals("")) {
            description = after.getDescription();
        } else description = before.getDescription();

        createdAt = before.getCreatedAt();
        updatedAt = after.getUpdatedAt();

        return new Book(after.getBookId(), bookName, category, price, description, createdAt, updatedAt);
    }

}

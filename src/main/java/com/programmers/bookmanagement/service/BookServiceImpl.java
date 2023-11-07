package com.programmers.bookmanagement.service;

import com.programmers.bookmanagement.model.Book;
import com.programmers.bookmanagement.model.Category;
import com.programmers.bookmanagement.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return bookRepository.update(book);
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
}

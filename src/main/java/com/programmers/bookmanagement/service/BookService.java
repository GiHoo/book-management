package com.programmers.bookmanagement.service;

import com.programmers.bookmanagement.model.Book;
import com.programmers.bookmanagement.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {

    Book createBook(String bookName, Category category, long price, String description);

    List<Book> getAllBooks();

    Book update(Book book);

    Optional<Book> findById(UUID bookId);

    Optional<Book> findByName(String bookName);

    Optional<Book> findByCategory(Category category);

    Book deleteById(UUID bookId);

}

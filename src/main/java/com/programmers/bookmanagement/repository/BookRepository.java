package com.programmers.bookmanagement.repository;

import com.programmers.bookmanagement.model.Book;
import com.programmers.bookmanagement.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {

    List<Book> findAll();

    Book insert(Book book);

    Book update(Book book);

    Optional<Book> findById(UUID bookId);

    Optional<Book> findByName(String book);

    Optional<Book> findByCategory(Category category);

    Book deleteById(UUID bookId);
}

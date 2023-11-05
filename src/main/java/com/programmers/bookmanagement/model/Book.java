package com.programmers.bookmanagement.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Book {
    private final UUID bookId;
    private final String bookName;
    private final Category category;
    private final long price;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Book(UUID bookId, String bookName, Category category, long price, String description) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Book(UUID bookId, String bookName, Category category, long price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

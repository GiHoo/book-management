package com.programmers.bookmanagement.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Book {
    private final UUID bookId;
    private final String productName;
    private final Category category;
    private final long price;
    private final String description;
    private final LocalDateTime createdAt;

    public Book(UUID bookId, String productName, Category category, long price, String description) {
        this.bookId = bookId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }
}
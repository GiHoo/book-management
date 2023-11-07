package com.programmers.bookmanagement.dto;

import com.programmers.bookmanagement.model.Category;

public record UpdateBookRequest(String bookId, String bookName, Category category, long price, String description, String createdAt) {
}

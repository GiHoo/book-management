package com.programmers.bookmanagement.dto;

import com.programmers.bookmanagement.model.Category;

public record CreateBookRequest(String bookName, Category category, long price, String description) {
}

package com.programmers.bookmanagement.model;

import java.util.UUID;

public record OrderItem(UUID bookId, Category category, long price, int quantity) {
}

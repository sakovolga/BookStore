package com.sakovolga.bookstore.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
public class CartItemDto {

    long cartItemId;
    long bookId;
    short quantity;
    String title;
    String author;
    short yearOfPublication;
    String publishingHouse;
    BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemDto that = (CartItemDto) o;
        return bookId == that.bookId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bookId);
    }
}

package com.sakovolga.bookstore.dto;

import lombok.Data;

import java.math.BigDecimal;

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
}

package com.sakovolga.bookstore.dto;

import lombok.Data;

@Data
public class CartItemDto {

    long bookId;
    int quantity;
}

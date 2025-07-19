package com.sakovolga.bookstore.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailDto {
    long bookId;
    String bookAuthor;
    String bookTitle;
    BigDecimal bookPrice;
    int quantity;
    BigDecimal orderDetailPrice;
}

package com.sakovolga.bookstore.dto;

import com.sakovolga.bookstore.entity.enums.Rating;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class ReviewDto {
    long reviewId;
    long bookId;
    long userId;
    String rating;
    String comment;
    LocalDateTime createdAt;



}

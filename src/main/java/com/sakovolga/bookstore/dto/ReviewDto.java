package com.sakovolga.bookstore.dto;

import com.sakovolga.bookstore.entity.enums.Rating;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ReviewDto {
    UUID reviewId;
    UUID bookID;
    UUID userId;
    Rating rating;
    String comment;
    LocalDateTime createdAt;
}

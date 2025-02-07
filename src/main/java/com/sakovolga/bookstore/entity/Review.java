package com.sakovolga.bookstore.entity;

import com.sakovolga.bookstore.entity.enums.Rating;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long reviewId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "rating")
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private Book book;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return reviewId == review.reviewId && Objects.equals(createdAt, review.createdAt) && rating == review.rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, createdAt, rating);
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", createdAt=" + createdAt +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", user=" + user.getSecondName() +
                ", book=" + book.getTitle() +
                '}';
    }
}

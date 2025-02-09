package com.sakovolga.bookstore.service.impl;

import com.sakovolga.bookstore.dto.ReviewDto;
import com.sakovolga.bookstore.entity.Book;
import com.sakovolga.bookstore.entity.Review;
import com.sakovolga.bookstore.entity.User;
import com.sakovolga.bookstore.exception.NothingFoundException;
import com.sakovolga.bookstore.mapper.ReviewMapper;
import com.sakovolga.bookstore.repository.BookRepository;
import com.sakovolga.bookstore.repository.ReviewRepository;
import com.sakovolga.bookstore.security.UserProvider;
import com.sakovolga.bookstore.service.ReviewService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;
    private final UserProvider userProvider;
    @PersistenceContext
    private EntityManager entityManager;
    private final ReviewMapper reviewMapper;
    private final BookRepository bookRepository;

    @Transactional
    public ReviewDto create(ReviewDto reviewDto){
        User user = entityManager.merge(userProvider.getCurrentUser());
        Review review = reviewMapper.toEntity(reviewDto);
        review.setUser(user);
        Book book = bookRepository.findById(reviewDto.getBookId())
                .orElseThrow(() -> new NothingFoundException("Book with id " + reviewDto.getBookId() + " does not exists"));
        review.setBook(book);
        updateBookRating(book, review.getRating().getValue());
        Review savedReview = repository.save(review);
        return reviewMapper.toDto(savedReview);
    }

    private void updateBookRating(Book book, Integer newRating) {
        BigDecimal currentRating = Objects.requireNonNullElse(book.getBookRating(), BigDecimal.ZERO);
        Integer reviewCount = Objects.requireNonNullElse(book.getReviewCount(), 0);

        BigDecimal newAverage = (currentRating.multiply(BigDecimal.valueOf(reviewCount))
                .add(BigDecimal.valueOf(newRating)))
                .divide(BigDecimal.valueOf(reviewCount + 1), 2, RoundingMode.HALF_UP);
        book.setBookRating(newAverage);
        book.setReviewCount(reviewCount + 1);
    }


}

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
        Review savedReview = repository.save(review);
        return reviewMapper.toDto(savedReview);
    }
}

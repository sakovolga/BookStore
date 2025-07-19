package com.sakovolga.bookstore.mapper;

import com.sakovolga.bookstore.dto.ReviewDto;
import com.sakovolga.bookstore.entity.Book;
import com.sakovolga.bookstore.entity.Review;
import com.sakovolga.bookstore.entity.enums.Rating;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Review toEntity(ReviewDto reviewDto);

    @Mapping(target = "bookId", source = "book.bookId")
    @Mapping(target = "userId", source = "user.userId")
    ReviewDto toDto(Review review);
}

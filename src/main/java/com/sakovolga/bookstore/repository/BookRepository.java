package com.sakovolga.bookstore.repository;

import com.sakovolga.bookstore.entity.Book;
import com.sakovolga.bookstore.entity.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByCategory(Category category);
}

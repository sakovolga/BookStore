package com.sakovolga.bookstore.repository;

import com.sakovolga.bookstore.entity.Book;
import com.sakovolga.bookstore.entity.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Set<Book> findAllByCategory(Category category);
}

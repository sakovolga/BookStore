package com.sakovolga.bookstore.service.impl;

import com.sakovolga.bookstore.entity.Book;
import com.sakovolga.bookstore.entity.enums.Category;
import com.sakovolga.bookstore.exception.NothingFoundException;
import com.sakovolga.bookstore.repository.BookRepository;
import com.sakovolga.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getAll() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new NothingFoundException("No books found");
        }
        return books;
    }

    @Override
    public Set<Book> getByCategory(String category) {
        try {
            Category categoryEnum = Category.valueOf(category.toUpperCase());
            Set<Book> books = bookRepository.findAllByCategory(categoryEnum);
            if (books.isEmpty()) {
                throw new NothingFoundException("No books found");
            }
            return books;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Category " + category + " does not exist.");
        }
    }

    @Override
    public Book getById(String id) {
        return bookRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NothingFoundException("There is no book with id " + id));
    }
}

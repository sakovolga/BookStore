package com.sakovolga.bookstore.service;

import com.sakovolga.bookstore.entity.Book;

import java.util.List;
import java.util.Set;

public interface BookService {
    List<Book> getAll();

    Set<Book> getByCategory(String category);

    Book getById(String id);
}

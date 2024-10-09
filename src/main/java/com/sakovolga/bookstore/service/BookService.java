package com.sakovolga.bookstore.service;

import com.sakovolga.bookstore.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    List<Book> getByCategory(String category);

    Book getById(String id);
}

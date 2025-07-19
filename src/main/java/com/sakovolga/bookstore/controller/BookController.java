package com.sakovolga.bookstore.controller;

import com.sakovolga.bookstore.entity.Book;
import com.sakovolga.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Set<Book>> getByCategory(@PathVariable (name = "category") String category){
        return ResponseEntity.ok(bookService.getByCategory(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable (name = "id") String id){
        return ResponseEntity.ok(bookService.getById(id));
    }
}

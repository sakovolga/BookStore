package com.sakovolga.bookstore.entity;

import com.sakovolga.bookstore.entity.enums.Category;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Book {
    @Id
    @Column(name = "book_id")
    private long bookId;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "year_of_publication")
    private short yearOfPublication;

    @Column(name = "publishing_house")
    private String publishingHouse;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;
}

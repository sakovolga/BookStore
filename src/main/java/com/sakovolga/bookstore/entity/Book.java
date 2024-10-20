package com.sakovolga.bookstore.entity;

import com.sakovolga.bookstore.entity.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "books")
@Getter
@Setter
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

    @Column(name = "reminder")
    private int reminder;

    @Column(name = "book_rating")
    private BigDecimal bookRating;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookId == book.bookId && yearOfPublication == book.yearOfPublication && Objects.equals(author, book.author) && Objects.equals(title, book.title) && Objects.equals(publishingHouse, book.publishingHouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, author, title, yearOfPublication, publishingHouse);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                ", publishingHouse='" + publishingHouse + '\'' +
                ", price=" + price +
                ", bookRating=" + bookRating +
                ", category=" + category +
                '}';
    }
}

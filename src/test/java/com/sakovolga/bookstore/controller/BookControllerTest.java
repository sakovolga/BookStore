package com.sakovolga.bookstore.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakovolga.bookstore.entity.Book;
import com.sakovolga.bookstore.entity.enums.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/drop-tables.sql")
@Sql("/create-tables.sql")
@Sql("/insert_test_data.sql")
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllTest() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/book"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Book> books = objectMapper.readValue(result, new TypeReference<>() {});
        Assertions.assertEquals(4, books.size());
    }

    @Test
    void getByCategoryTest() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/book/category/ADVENTURE"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Set<Book> books = objectMapper.readValue(result, new TypeReference<>() {});
        Assertions.assertEquals(2, books.size());
    }

    @Test
    void getByIdTest() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/book/4"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Book actualBook = objectMapper.readValue(result, Book.class);
        Book expectedBook = getBook();

        Assertions.assertEquals(expectedBook, actualBook);
    }

    private Book getBook(){
        Book book = new Book();
        book.setBookId(4);
        book.setAuthor("Jane Austen");
        book.setTitle("Pride and Prejudice");
        book.setCategory(Category.ROMANCE);
        book.setReminder(20);
        book.setPrice(BigDecimal.valueOf(25.50));
        book.setPublishingHouse("T. Egerton, Whitehall");
        book.setYearOfPublication((short) 1813);
        return book;
    }
}
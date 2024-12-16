package com.sakovolga.bookstore.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakovolga.bookstore.dto.CartItemDto;
import com.sakovolga.bookstore.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/drop-tables.sql")
@Sql("/create-tables.sql")
@Sql("/insert_test_data.sql")
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @MethodSource("provideCartItemDtos")
    @WithUserDetails(value = "petr@mail.com")
    void addToCartTest(CartItemDto cartItemDto) throws Exception {
        String cartItemDtoJson = objectMapper.writeValueAsString(cartItemDto);

        Book bookBefore = getBook(cartItemDto.getBookId());

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cartItemDtoJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Book bookAfter = getBook(cartItemDto.getBookId());

        List<CartItemDto> actualCart = getActualCart();
        CartItemDto actualCartItemDto = actualCart.stream()
                .filter(ciDto -> Objects.equals(ciDto.getBookId(), cartItemDto.getBookId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No cart item found with bookId: " + cartItemDto.getBookId()));

        Assertions.assertTrue(actualCart.contains(cartItemDto));
        Assertions.assertTrue(actualCartItemDto.getQuantity() >= cartItemDto.getQuantity());
        Assertions.assertEquals(bookBefore.getReminder() - cartItemDto.getQuantity(), bookAfter.getReminder());
    }

    @Test
    @WithUserDetails(value = "petr@mail.com")
    void getCartTest() throws Exception {
        List<CartItemDto> cart = getActualCart();
        Assertions.assertEquals(cart.size(), 1);
    }

    static Stream<CartItemDto> provideCartItemDtos() {
        return Stream.of(
                getCartItemDto1(),
                getCartItemDto2()
        );
    }

    static CartItemDto getCartItemDto1() {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setBookId(1);
        cartItemDto.setQuantity((short) 2);
        return cartItemDto;
    }

    static CartItemDto getCartItemDto2() {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setBookId(4);
        cartItemDto.setQuantity((short) 1);
        return cartItemDto;
    }

    private List<CartItemDto> getActualCart() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(result, new TypeReference<>() {
        });
    }

    private Book getBook(long bookId) throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/book/" + bookId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(result, Book.class);
    }
}
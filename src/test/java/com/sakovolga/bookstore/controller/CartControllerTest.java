package com.sakovolga.bookstore.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakovolga.bookstore.dto.CartItemDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

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

    @Test
    @WithUserDetails(value = "jurij@mail.com")
    void addToCart() throws Exception {
        CartItemDto cartItemDto = getCartItemDto();
        String cartItemDtoJson = objectMapper.writeValueAsString(cartItemDto);

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cartItemDtoJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<CartItemDto> actualCart = getActualCart();

        Assertions.assertTrue(actualCart.contains(cartItemDto));

    }

    @Test
    void getCart() {
    }

    private CartItemDto getCartItemDto() {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setBookId(1);
        cartItemDto.setQuantity((short) 2);
        return cartItemDto;
    }

    private List<CartItemDto> getActualCart() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(result, new TypeReference<>() {});
    }
}
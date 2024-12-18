package com.sakovolga.bookstore.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakovolga.bookstore.dto.CartItemDto;
import com.sakovolga.bookstore.dto.MyOrderDto;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/drop-tables.sql")
@Sql("/create-tables.sql")
@Sql("/insert_test_data.sql")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "petr@mail.com")
    void createTest() throws Exception {

        List<MyOrderDto> ordersBefore = getMyOrders();
        List<CartItemDto> cartBefore = getActualCart();

        List<Long> cartItemIds = List.of(1L);

        String cartItemIdsJson = objectMapper.writeValueAsString(cartItemIds);

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cartItemIdsJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<MyOrderDto> ordersAfter = getMyOrders();
        List<CartItemDto> cartAfter = getActualCart();

        Assertions.assertEquals(ordersBefore.size() + 1, ordersAfter.size());
        Assertions.assertEquals(cartBefore.size() - cartItemIds.size(), cartAfter.size());
    }

    private List<MyOrderDto> getMyOrders() throws Exception {
        String myOrdersJson = mockMvc.perform(MockMvcRequestBuilders.get("/order/myorders"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(myOrdersJson, new TypeReference<>() {});
    }

    private List<CartItemDto> getActualCart() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(result, new TypeReference<>() {
        });
    }
}
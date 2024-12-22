package com.sakovolga.bookstore.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakovolga.bookstore.dto.*;
import com.sakovolga.bookstore.entity.enums.OrderStatus;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        List<Long> ordersBefore = getMyOrders();
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

        OrderDto actualOrderDto = objectMapper.readValue(result, OrderDto.class);

        List<Long> ordersAfter = getMyOrders();
        List<CartItemDto> cartAfter = getActualCart();

        Assertions.assertEquals(ordersBefore.size() + 1, ordersAfter.size());
        Assertions.assertEquals(cartBefore.size() - cartItemIds.size(), cartAfter.size());
    }



    @Test
    @WithUserDetails(value = "petr@mail.com")
    void getMyOrdersTest() throws Exception {
        List<Long> orders = getMyOrders();

        Assertions.assertEquals(1, orders.size());
//        Assertions.assertEquals(OrderStatus.COMPLETED, orders.getFirst().getStatus());
    }

    @Test
    @WithUserDetails(value = "petr@mail.com")
    void getOrderTest() throws Exception {
        OrderDto expectedOrderDto = getOrderDto();
        long id = 1L;

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/order/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OrderDto actualOrderDto = objectMapper.readValue(result, OrderDto.class);

        Assertions.assertEquals(expectedOrderDto, actualOrderDto);
    }
//
//    @Test
//    @WithUserDetails(value = "anna@mail.com")
//    void getOrdersTest() throws Exception{
//        List<OrderForManagerDto> expectedOrders = getAll();
//
//        String result = mockMvc.perform(MockMvcRequestBuilders.get("/order/all"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        List<OrderForManagerDto> actualOrders = objectMapper.readValue(result, new TypeReference<>() {});
//
//        Assertions.assertEquals(expectedOrders, actualOrders);
//    }

    private List<Long> getMyOrders() throws Exception {
        String myOrdersJson = mockMvc.perform(MockMvcRequestBuilders.get("/order/myorders"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Long> list =  objectMapper.readValue(myOrdersJson, new TypeReference<>() {});
        System.out.println("111111111111111111" + list);
        return list;
    }

    private List<CartItemDto> getActualCart() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(result, new TypeReference<>() {
        });
    }

    private OrderDto getOrderDto(){
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(1);
        orderDto.setTotalPrice(BigDecimal.valueOf(45.55));
        orderDto.setStatus(OrderStatus.COMPLETED);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        orderDto.setCreatedAt(LocalDateTime.parse("2024-09-20 13:00:00", formatter));
        orderDto.setCompletedAt(LocalDateTime.parse("2024-09-20 14:00:00", formatter));
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setBookId(1);
        orderDetailDto.setBookTitle("Harry Potter");
        orderDetailDto.setBookAuthor("Joahn Roaling");
        orderDetailDto.setBookPrice(BigDecimal.valueOf(45.55));
        orderDetailDto.setOrderDetailPrice(BigDecimal.valueOf(45.55));
        orderDetailDto.setQuantity(1);
        orderDto.setList(List.of(orderDetailDto));
        return orderDto;
    }

//    private List<OrderForManagerDto> getAll(){
//        OrderForManagerDto orderForManagerDto1 = new OrderForManagerDto();
//        orderForManagerDto1.setOrderId(1L);
//        orderForManagerDto1.setUserId(1L);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        orderForManagerDto1.setCreatedAt(LocalDateTime.parse("2024-09-20 13:00:00", formatter));
//        orderForManagerDto1.setCompletedAt(LocalDateTime.parse("2024-09-20 14:00:00", formatter));
//        orderForManagerDto1.setStatus(OrderStatus.COMPLETED);
//        orderForManagerDto1.setTotalPrice(BigDecimal.valueOf(45.55));
//        OrderDetailDto orderDetailDto1 = new OrderDetailDto();
//        orderDetailDto1.setOrderDetailPrice(BigDecimal.valueOf(45.55));
//        orderDetailDto1.setQuantity(1);
//        orderDetailDto1.setBookPrice(BigDecimal.valueOf(45.55));
//        orderDetailDto1.setBookId(1L);
//        orderDetailDto1.setBookTitle("Harry Potter");
//        orderDetailDto1.setBookAuthor("Joahn Roaling");
//        orderForManagerDto1.setOrderDetails(List.of(orderDetailDto1));
//
//        OrderForManagerDto orderForManagerDto2 = new OrderForManagerDto();
//        orderForManagerDto2.setOrderId(2L);
//        orderForManagerDto2.setUserId(2L);
//        orderForManagerDto2.setCreatedAt(LocalDateTime.parse("2024-09-20 13:00:00", formatter));
//        orderForManagerDto2.setCompletedAt(LocalDateTime.parse("2024-09-20 14:00:00", formatter));
//        orderForManagerDto2.setStatus(OrderStatus.COMPLETED);
//        orderForManagerDto2.setTotalPrice(BigDecimal.valueOf(45.55));
//        OrderDetailDto orderDetailDto2 = new OrderDetailDto();
//        orderDetailDto2.setOrderDetailPrice(BigDecimal.valueOf(45.55));
//        orderDetailDto2.setQuantity(1);
//        orderDetailDto2.setBookPrice(BigDecimal.valueOf(45.55));
//        orderDetailDto2.setBookId(1L);
//        orderDetailDto2.setBookTitle("Harry Potter");
//        orderDetailDto2.setBookAuthor("Joahn Roaling");
//        orderForManagerDto2.setOrderDetails(List.of(orderDetailDto1));
//
//        return List.of(orderForManagerDto1, orderForManagerDto2);
//    }
}
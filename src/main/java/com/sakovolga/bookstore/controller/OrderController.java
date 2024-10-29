package com.sakovolga.bookstore.controller;

import com.sakovolga.bookstore.dto.OrderDto;
import com.sakovolga.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderDto create(@RequestBody List<Long> cartItemIds){
        OrderDto orderDto = orderService.createOrder(cartItemIds);
        System.out.println("11 11 11 11 11 11 1 11 11 ");
        return orderDto;
    }
}

package com.sakovolga.bookstore.controller;

import com.sakovolga.bookstore.dto.OrderDto;
import com.sakovolga.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    public OrderDto create(List<Long> cartItemIds){
        return orderService.createOrder(cartItemIds);
    }
}

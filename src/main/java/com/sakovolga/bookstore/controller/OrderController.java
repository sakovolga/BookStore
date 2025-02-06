package com.sakovolga.bookstore.controller;

import com.sakovolga.bookstore.dto.OrderDto;
import com.sakovolga.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody List<Long> cartItemIds){
        return orderService.createOrder(cartItemIds);
    }

    @GetMapping("/myorders")
    public List<Long> getMyOrders(){
        return orderService.getMyOrders();
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable Long id){
        return orderService.getOrder(id);
    }
}

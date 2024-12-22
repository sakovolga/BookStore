package com.sakovolga.bookstore.service;

import com.sakovolga.bookstore.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(List<Long> cartItemIds);

    List<Long> getMyOrders();

//    List<OrderForManagerDto> getOrders();
}

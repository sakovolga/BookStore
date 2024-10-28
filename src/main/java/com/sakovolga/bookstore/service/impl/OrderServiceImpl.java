package com.sakovolga.bookstore.service.impl;

import com.sakovolga.bookstore.dto.OrderDto;
import com.sakovolga.bookstore.repository.OrderRepository;
import com.sakovolga.bookstore.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public OrderDto createOrder(List<Long> cartItemIds) {
        return null;
    }
}

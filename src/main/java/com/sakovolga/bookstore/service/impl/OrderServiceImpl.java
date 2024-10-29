package com.sakovolga.bookstore.service.impl;

import com.sakovolga.bookstore.dto.OrderDto;
import com.sakovolga.bookstore.entity.CartItem;
import com.sakovolga.bookstore.entity.Order;
import com.sakovolga.bookstore.entity.User;
import com.sakovolga.bookstore.exception.NotAllCartItemsFoundException;
import com.sakovolga.bookstore.mapper.OrderDetailMapper;
import com.sakovolga.bookstore.mapper.OrderMapper;
import com.sakovolga.bookstore.repository.CartItemRepository;
import com.sakovolga.bookstore.repository.OrderRepository;
import com.sakovolga.bookstore.security.UserProvider;
import com.sakovolga.bookstore.service.OrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserProvider userProvider;
    @PersistenceContext
    private EntityManager entityManager;
    private final OrderDetailMapper orderDetailMapper;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderDto createOrder(List<Long> cartItemIds) {
//        if (!cartItemRepository.allExists(cartItemIds, cartItemIds.size())) {
//            throw new NotAllCartItemsFoundException("Not all cart items found");
//        }
        List<CartItem> cartItems = cartItemRepository.findAllByIds(cartItemIds);
        if (cartItems.size() != cartItemIds.size()) {
            throw new NotAllCartItemsFoundException("Not all cart items found");
        }
        User user = entityManager.merge(userProvider.getCurrentUser());
        Order order = new Order();
        order.setOrderDetailList(List.of());
        order.setUser(user);
        for (CartItem cartItem : cartItems){
            order.getOrderDetailList().add(orderDetailMapper.toOrderDetail(cartItem));
        }
        Order savedOrder = orderRepository.saveAndFlush(order);
        return orderMapper.toDto(savedOrder);
    }
}
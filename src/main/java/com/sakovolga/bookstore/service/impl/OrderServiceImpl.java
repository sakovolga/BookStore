package com.sakovolga.bookstore.service.impl;

import com.sakovolga.bookstore.dto.OrderDto;
import com.sakovolga.bookstore.entity.CartItem;
import com.sakovolga.bookstore.entity.Order;
import com.sakovolga.bookstore.entity.OrderDetail;
import com.sakovolga.bookstore.entity.User;
import com.sakovolga.bookstore.entity.enums.OrderStatus;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        System.out.println("1111111111111");
        List<CartItem> cartItems = cartItemRepository.findAllByIds(cartItemIds);
        if (cartItems.size() != cartItemIds.size()) {
            throw new NotAllCartItemsFoundException("Not all cart items found");
        }
        System.out.println("222222222222222");
        User user = entityManager.merge(userProvider.getCurrentUser());
        System.out.println("33333333333333333");
        Order order = new Order();
        System.out.println("4444444444444444444");
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        System.out.println("5555555555555555");
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItem cartItem : cartItems){
            OrderDetail orderDetail = orderDetailMapper.toOrderDetail(cartItem);
            orderDetail.setOrder(order);
            System.out.println("666666666666666666");
            orderDetails.add(orderDetail);
        }
        order.setOrderDetailList(orderDetails);
        System.out.println("7777777777777777777777777" + order);
        Order savedOrder = orderRepository.saveAndFlush(order);
        System.out.println("888888888888888888");
        cartItemRepository.deleteAllByCartItemIdIn(cartItemIds);
        System.out.println("999999999999999999999999");
        OrderDto orderDto = orderMapper.toDto(savedOrder);
        System.out.println("101010101010101010101010");
        return orderDto;
    }
}

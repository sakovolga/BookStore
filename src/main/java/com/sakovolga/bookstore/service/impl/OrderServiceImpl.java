package com.sakovolga.bookstore.service.impl;

import com.sakovolga.bookstore.dto.OrderDto;
import com.sakovolga.bookstore.entity.CartItem;
import com.sakovolga.bookstore.entity.Order;
import com.sakovolga.bookstore.entity.OrderDetail;
import com.sakovolga.bookstore.entity.User;
import com.sakovolga.bookstore.entity.enums.OrderStatus;
import com.sakovolga.bookstore.exception.NotAllCartItemsFoundException;
import com.sakovolga.bookstore.exception.NothingFoundException;
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
        User user = entityManager.merge(userProvider.getCurrentUser());
        List<CartItem> cartItems = cartItemRepository.findAllByIds(cartItemIds);
        List<CartItem> cartItemsChecked = cartItems.stream()
                .filter(cartItem -> cartItem.getUser().equals(user))
                .toList();
        if (cartItemIds.size() != cartItemsChecked.size()) {
            throw new NotAllCartItemsFoundException("Not all cart items found");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetail = orderDetailMapper.toOrderDetail(cartItem);
            orderDetail.setOrder(order);
            orderDetails.add(orderDetail);
        }
        order.setOrderDetailList(orderDetails);
        Order savedOrder = orderRepository.saveAndFlush(order);
        cartItemRepository.deleteAllByCartItemIdIn(cartItemIds);
        return orderMapper.toDto(savedOrder);
    }

    @Transactional
    @Override
    public List<Long> getMyOrders() {
        User user = entityManager.merge(userProvider.getCurrentUser());
        return orderRepository.findIdByUser(user);
//        List<Order> orderList = orderRepository.findAllByUser(user);
//        return orderList.stream()
//                .map(orderMapper::toMyOrderDto)
//                .toList();
    }

    @Transactional
    @Override
    public OrderDto getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Order with id " + id + " does not exists"));
        return orderMapper.toDto(order);
    }

//    @Override
//    public List<OrderForManagerDto> getOrders() {
//        List<Order> orderList = orderRepository.findAll();
//        return orderList.stream()
//                .map(orderMapper::toOrderForManagerDto)
//                .toList();
//    }
}

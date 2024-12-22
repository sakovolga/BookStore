package com.sakovolga.bookstore.dto;

import com.sakovolga.bookstore.entity.enums.OrderStatus;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    long orderId;
    List<OrderDetailDto> list;
    BigDecimal totalPrice;
    LocalDateTime createdAt;
    LocalDateTime completedAt;
    OrderStatus status;
}

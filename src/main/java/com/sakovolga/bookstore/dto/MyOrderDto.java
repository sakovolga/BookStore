package com.sakovolga.bookstore.dto;

import com.sakovolga.bookstore.entity.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MyOrderDto {
    Long orderId;
    LocalDateTime date;
    OrderStatus status;
    BigDecimal totalPrice;
}

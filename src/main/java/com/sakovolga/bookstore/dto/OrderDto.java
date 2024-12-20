package com.sakovolga.bookstore.dto;

import com.sakovolga.bookstore.entity.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    long orderId;
    List<OrderDetailDto> list;
    BigDecimal totalPrice;
}

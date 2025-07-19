package com.sakovolga.bookstore.mapper;

import com.sakovolga.bookstore.dto.OrderDetailDto;
import com.sakovolga.bookstore.dto.OrderDto;
import com.sakovolga.bookstore.entity.Book;
import com.sakovolga.bookstore.entity.Order;
import com.sakovolga.bookstore.entity.OrderDetail;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mappings(
            @Mapping(target = "status", source = "status")
    )
    OrderDto toDto(Order order);

    @AfterMapping
    default void generateOrderDto(@MappingTarget OrderDto orderDto, Order order) {
        orderDto.setOrderId(order.getOrderId());
        List<OrderDetailDto> orderDetailDtos = order.getOrderDetailList().stream().map(this::generateOrderDetailDto).toList();
        orderDto.setList(orderDetailDtos);
        BigDecimal totalPrice = orderDetailDtos.stream()
                .map(OrderDetailDto::getOrderDetailPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderDto.setTotalPrice(totalPrice);
    }

    default OrderDetailDto generateOrderDetailDto(OrderDetail orderDetail) {
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        Book book = orderDetail.getBook();
        orderDetailDto.setBookId(book.getBookId());
        orderDetailDto.setBookAuthor(book.getAuthor());
        orderDetailDto.setBookTitle(book.getTitle());
        orderDetailDto.setBookPrice(book.getPrice());
        orderDetailDto.setQuantity(orderDetail.getQuantity());
        orderDetailDto.setOrderDetailPrice(book.getPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity())));
        return orderDetailDto;
    }

//    @Mappings(
//            @Mapping(target = "orderId", source = "orderId")
//    )
//    MyOrderDto toMyOrderDto(Order order);
//
//    @AfterMapping
//    default void generateMyOrderDto(@MappingTarget MyOrderDto myOrdersDto, Order order) {
//        LocalDateTime createdAt = order.getCreatedAt();
//        Optional<LocalDateTime> completedAt = Optional.ofNullable(order.getCompletedAt());
//        myOrdersDto.setDate(completedAt.orElse(createdAt));
//        myOrdersDto.setTotalPrice(getTotalPrice(order));
//    }
//
//    default BigDecimal getTotalPrice(Order order) {
//        return order.getOrderDetailList()
//                .stream()
//                .map(orderDetail ->
//                        orderDetail.getBook().getPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    @Mappings(
//            @Mapping(target = "orderId", source = "orderId")
//    )
//    OrderForManagerDto toOrderForManagerDto(Order order);
//
//    @AfterMapping
//    default void generateOrderForManagerDto(@MappingTarget OrderForManagerDto orderForManagerDto, Order order){
//        orderForManagerDto.setTotalPrice(getTotalPrice(order));
//        orderForManagerDto.setUserId(order.getUser().getUserId());
//    }

}

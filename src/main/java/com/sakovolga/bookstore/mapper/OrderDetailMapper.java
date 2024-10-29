package com.sakovolga.bookstore.mapper;

import com.sakovolga.bookstore.entity.CartItem;
import com.sakovolga.bookstore.entity.OrderDetail;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDetailMapper {

    OrderDetail toOrderDetail(CartItem cartItem);

    @AfterMapping
    default void generateOrderDetail(@MappingTarget OrderDetail orderDetail, CartItem cartItem){
        orderDetail.setBook(cartItem.getBook());
        orderDetail.setQuantity(cartItem.getQuantity());
    }
}

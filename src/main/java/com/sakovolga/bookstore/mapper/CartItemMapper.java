package com.sakovolga.bookstore.mapper;

import com.sakovolga.bookstore.dto.CartItemDto;
import com.sakovolga.bookstore.entity.CartItem;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper {

    CartItemDto toDto(CartItem cartItem);

    @AfterMapping
    default void generateCartItemDto(@MappingTarget CartItemDto cartItemDto, CartItem cartItem){
        cartItemDto.setBookId(cartItem.getBook().getBookId());
        cartItemDto.setTitle(cartItem.getBook().getTitle());
        cartItemDto.setAuthor(cartItem.getBook().getAuthor());
        cartItemDto.setYearOfPublication(cartItem.getBook().getYearOfPublication());
        cartItemDto.setPublishingHouse(cartItem.getBook().getPublishingHouse());
        cartItemDto.setPrice(cartItem.getBook().getPrice());
        cartItemDto.setCartItemId(cartItem.getCartItemId());
        cartItemDto.setQuantity(cartItem.getQuantity());
    }

}

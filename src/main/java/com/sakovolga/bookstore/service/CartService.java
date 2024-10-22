package com.sakovolga.bookstore.service;

import com.sakovolga.bookstore.dto.CartItemDto;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface CartService {
    void addToCart(CartItemDto cartItemDto) throws BadRequestException;

     List<CartItemDto> getCart();
}

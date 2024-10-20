package com.sakovolga.bookstore.service;

import com.sakovolga.bookstore.dto.CartItemDto;

public interface CartService {
    int addToCart(CartItemDto cartItemDto);
}

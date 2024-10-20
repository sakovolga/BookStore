package com.sakovolga.bookstore.service.impl;

import com.sakovolga.bookstore.dto.CartItemDto;
import com.sakovolga.bookstore.repository.CartItemRepository;
import com.sakovolga.bookstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;

    @Override
    public int addToCart(CartItemDto cartItemDto) {

        return 0;
    }
}

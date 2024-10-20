package com.sakovolga.bookstore.controller;

import com.sakovolga.bookstore.dto.CartItemDto;
import com.sakovolga.bookstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Integer> addToCart(@RequestBody CartItemDto cartItemDto){
        return ResponseEntity.ok(cartService.addToCart(cartItemDto));
    }
}

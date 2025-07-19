package com.sakovolga.bookstore.controller;

import com.sakovolga.bookstore.dto.CartItemDto;
import com.sakovolga.bookstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addToCart(@RequestBody CartItemDto cartItemDto) throws BadRequestException {
        cartService.addToCart(cartItemDto);
    }

    @GetMapping
    public List<CartItemDto> getCart(){
        return cartService.getCart();
    }
}

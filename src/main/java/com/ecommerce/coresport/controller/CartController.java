package com.ecommerce.coresport.controller;

import com.ecommerce.coresport.mapper.CartMapper;
import com.ecommerce.coresport.model.CartResponse;
import com.ecommerce.coresport.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/carts")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartService cartService;
    CartMapper cartMapper;

    @GetMapping
    public ResponseEntity<List<CartResponse>> getAllCarts() {
        List<CartResponse> carts = cartService.getAllCarts();
        return ResponseEntity.ok().body(carts);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable("cartId") String cartId) {
        CartResponse cart = cartService.getCartById(cartId);
        return ResponseEntity.ok().body(cart);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable("cartId") String cartId) {
        cartService.deleteCartById(cartId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CartResponse> createCart(@RequestBody CartResponse cartResponse) {
        CartResponse createdCart = cartService.createCart(cartMapper.toCart(cartResponse));
        return ResponseEntity.status(201).body(createdCart);
    }
}

package com.ecommerce.coresport.service;

import com.ecommerce.coresport.entity.Cart;
import com.ecommerce.coresport.model.CartResponse;

import java.util.List;

public interface CartService {
    List<CartResponse> getAllCarts();

    CartResponse getCartById(String cartId);

    void deleteCartById(String cartId);

    CartResponse createCart(Cart cart);
}

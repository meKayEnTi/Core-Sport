package com.ecommerce.coresport.service.implementation;

import com.ecommerce.coresport.mapper.CartMapper;
import com.ecommerce.coresport.repository.CartRepository;
import com.ecommerce.coresport.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import com.ecommerce.coresport.entity.Cart;
import com.ecommerce.coresport.model.CartResponse;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    CartMapper cartMapper;
    // Implement the methods defined in CartService interface here

    @Override
    public List<CartResponse> getAllCarts() {
        // Logic to retrieve all carts
        return cartMapper.toCartResponseList((List<Cart>) cartRepository.findAll());
    }

    @Override
    public CartResponse getCartById(String cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if(cart.isPresent()) {
            return cartMapper.toCartResponse(cart.get());
        } else {
            log.error("Cart with ID {} not found", cartId);
            return null;
        }
    }

    @Override
    public void deleteCartById(String cartId){
        cartRepository.deleteById(cartId);
    }

    @Override
    public CartResponse createCart(Cart cart) {
        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toCartResponse(savedCart);
    }
}

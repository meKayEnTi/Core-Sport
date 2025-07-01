package com.ecommerce.coresport.mapper;

import com.ecommerce.coresport.entity.Cart;
import com.ecommerce.coresport.model.CartResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {
    CartResponse toCartResponse(Cart cart);

    List<CartResponse> toCartResponseList(List<Cart> carts);

    Cart toCart(CartResponse cartResponse);
}

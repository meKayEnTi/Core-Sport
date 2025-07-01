package com.ecommerce.coresport.mapper;

import com.ecommerce.coresport.entity.CartItem;
import com.ecommerce.coresport.model.CartItemResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemResponse toCartItemResponse(CartItem cartItem);

    CartItem toCartItem(CartItemResponse cartItemResponse);
}

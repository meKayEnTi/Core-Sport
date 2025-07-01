package com.ecommerce.coresport.model;

import com.ecommerce.coresport.entity.OrderAggregate.ShippingAddress;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    String cartId;
    ShippingAddress shippingAddress;
    Double subTotal;
    Double deliveryFee;
    LocalDateTime orderDate;
}

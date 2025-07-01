package com.ecommerce.coresport.entity;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("CartItem")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CartItem {
    @Id
    Integer id;
    String name;
    String description;
    Long price;
    String pictureUrl;
    String productBrand;
    String productType;
    Integer quantity;
}

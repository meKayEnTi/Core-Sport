package com.ecommerce.coresport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RedisHash("Cart")
public class Cart {
    @Id
    @NonNull
    String id;
    List<CartItem> items = new ArrayList<>();

}

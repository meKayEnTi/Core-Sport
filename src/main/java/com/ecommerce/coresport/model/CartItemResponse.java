package com.ecommerce.coresport.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    Integer id;
    String name;
    String description;
    Long price;
    String pictureUrl;
    String productBrand;
    String productType;
    Integer quantity;
}

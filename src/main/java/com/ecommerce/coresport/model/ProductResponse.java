package com.ecommerce.coresport.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Integer id;
    String name;
    String description;
    Double price;
    String pictureUrl;
    String productType;
    String productBrand;
}

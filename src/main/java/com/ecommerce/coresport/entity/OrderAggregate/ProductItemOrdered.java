package com.ecommerce.coresport.entity.OrderAggregate;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductItemOrdered {
    Integer productId;
    String name;
    String pictureUrl;
}

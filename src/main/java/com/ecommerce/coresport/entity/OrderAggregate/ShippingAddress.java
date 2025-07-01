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
public class ShippingAddress {
    String name;
    String address1;
    String address2;
    String city;
    String state;
    String zipcode;
    String country;
}

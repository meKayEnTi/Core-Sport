package com.ecommerce.coresport.entity.OrderAggregate;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "cart_id")
    String cartId;

    @Embedded
    ShippingAddress shippingAddress;

    @Column(name = "order_date")
    LocalDateTime orderDate = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    List<OrderItem> items;

    @Column(name = "sub_total")
    Double subTotal;

    @Column(name = "delivery_fee")
    Long deliveryFee;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    OrderStatus orderStatus = OrderStatus.Pending;

    public Double getTotal() {
        return getSubTotal() + getDeliveryFee();
    }

}

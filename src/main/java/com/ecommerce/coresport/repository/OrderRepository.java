package com.ecommerce.coresport.repository;

import com.ecommerce.coresport.entity.OrderAggregate.Order;
import com.ecommerce.coresport.entity.OrderAggregate.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCartId(String cartId);

    List<Order> findByOrderStatus(OrderStatus status);

    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT o FROM Order o JOIN o.items i WHERE i.itemOrdered.name LIKE %:productName%")
    List<Order> findByProductName(@Param("productName") String productName);

    // Example: Find orders by shipping address
    @Query("SELECT o FROM Order o WHERE o.shippingAddress.city = :city")
    List<Order> findByShippingAddressCity(@Param("city") String city);

}

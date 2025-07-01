package com.ecommerce.coresport.service;

import com.ecommerce.coresport.entity.OrderAggregate.Order;
import com.ecommerce.coresport.model.OrderReponse;
import com.ecommerce.coresport.model.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    OrderReponse getOrderById(Integer orderId);

    List<OrderReponse> getAllOrders();

    Page<OrderReponse> getAllOrders(Pageable pageable);

    Integer createOrder(OrderRequest request);

    void deleteOrder(Integer orderId);
}

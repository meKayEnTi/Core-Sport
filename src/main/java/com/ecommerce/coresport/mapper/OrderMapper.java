package com.ecommerce.coresport.mapper;

import com.ecommerce.coresport.entity.OrderAggregate.Order;
import com.ecommerce.coresport.model.OrderReponse;
import com.ecommerce.coresport.model.OrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface OrderMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "cartId", target = "cartId")
    @Mapping(source = "shippingAddress", target = "shippingAddress")
    @Mapping(source = "subTotal", target = "subTotal")
    @Mapping(source = "deliveryFee", target = "deliveryFee")
    @Mapping(target = "total", expression = "java(order.getSubTotal() + order.getDeliveryFee())")
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "orderStatus", constant = "Pending")
    OrderReponse toOrderResponse(Order order);

    @Mapping(target = "orderDate", expression = "java(orderRequest.getOrderDate())")
    @Mapping(target = "orderStatus", constant = "Pending") // Reference enum constant directly
    Order orderResponseToOrder(OrderRequest orderRequest);

    List<OrderRequest> ordersToOrderResponses(List<Order> orders);

    void updateOrderFromOrderResponse(OrderRequest orderRequest, @MappingTarget Order order);
}

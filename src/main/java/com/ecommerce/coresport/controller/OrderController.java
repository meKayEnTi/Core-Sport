package com.ecommerce.coresport.controller;

import com.ecommerce.coresport.entity.OrderAggregate.Order;
import com.ecommerce.coresport.model.OrderReponse;
import com.ecommerce.coresport.model.OrderRequest;
import com.ecommerce.coresport.service.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/orders")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderReponse> getOrderById(@PathVariable("orderId") Integer orderId){
        OrderReponse order = orderService.getOrderById(orderId);
        if(order != null)
            return ResponseEntity.ok().body(order);
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<OrderReponse>> getAllOrders(){
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<OrderReponse>> getAllOrders(Pageable pageable){
        return ResponseEntity.ok().body(orderService.getAllOrders(pageable));
    }

    @PostMapping
    public ResponseEntity<Integer> createOrder(@Valid @RequestBody OrderRequest orderRequest){
        Integer orderId = orderService.createOrder(orderRequest);
        if(orderId != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable("orderId") Integer orderId){
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }



}

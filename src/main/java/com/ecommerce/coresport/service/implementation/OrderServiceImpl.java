package com.ecommerce.coresport.service.implementation;

import com.ecommerce.coresport.entity.CartItem;
import com.ecommerce.coresport.entity.OrderAggregate.Order;
import com.ecommerce.coresport.entity.OrderAggregate.OrderItem;
import com.ecommerce.coresport.entity.OrderAggregate.ProductItemOrdered;
import com.ecommerce.coresport.entity.Product;
import com.ecommerce.coresport.mapper.OrderMapper;
import com.ecommerce.coresport.model.CartItemResponse;
import com.ecommerce.coresport.model.CartResponse;
import com.ecommerce.coresport.model.OrderReponse;
import com.ecommerce.coresport.model.OrderRequest;
import com.ecommerce.coresport.repository.BrandRepository;
import com.ecommerce.coresport.repository.OrderRepository;
import com.ecommerce.coresport.repository.TypeRepository;
import com.ecommerce.coresport.service.CartService;
import com.ecommerce.coresport.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    OrderMapper orderMapper;
    CartService cartService;
    TypeRepository typeRepository;
    BrandRepository brandRepository;

    @Override
    public OrderReponse getOrderById(Integer orderId) {
        return orderRepository.findById(orderId).map(orderMapper::toOrderResponse).orElse(null);
    }

    @Override
    public List<OrderReponse> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toOrderResponse).toList();
    }

    @Override
    public Page<OrderReponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toOrderResponse);
    }

    @Override
    public Integer createOrder(OrderRequest request) {
        CartResponse cart = cartService.getCartById(request.getCartId());
        if(cart == null){
            log.error("Cart with ID {} not found.", request.getCartId());
            return null;
        }
        List<OrderItem> items = cart.getItems().stream().map(this::cartItemToOrderItem).toList();
        double subTotal = cart.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();

        Order order = orderMapper.orderResponseToOrder(request);
        order.setSubTotal(subTotal);
        order.setItems(items);

        Order savedOrder = orderRepository.save(order);
        cartService.deleteCartById(request.getCartId());
        return savedOrder.getId();
    }

    @Override
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }

    private OrderItem cartItemToOrderItem(CartItemResponse cartItemResponse){
        if(cartItemResponse != null){
            return OrderItem.builder()
                    .itemOrdered(cartItemToProduct(cartItemResponse))
                    .quantity(cartItemResponse.getQuantity())
                    .build();
        }else {
            return null;
        }
    }

    private ProductItemOrdered cartItemToProduct(CartItemResponse cartItemResponse){
        return ProductItemOrdered.
                builder()
                .productId(cartItemResponse.getId())
                .name(cartItemResponse.getName())
                .pictureUrl(cartItemResponse.getPictureUrl())
                .build();
    }
}

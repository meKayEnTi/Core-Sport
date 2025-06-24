package com.ecommerce.coresport.service;

import com.ecommerce.coresport.model.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse getProductById(Integer productId);

    List<ProductResponse> getAllProducts();
}

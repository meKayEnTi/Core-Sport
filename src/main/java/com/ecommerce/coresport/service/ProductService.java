package com.ecommerce.coresport.service;

import com.ecommerce.coresport.model.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponse getProductById(Integer productId);

    Page<ProductResponse> getAllProducts(Pageable pageable, Integer brandId, Integer typeId, String keyword);
}

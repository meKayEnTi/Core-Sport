package com.ecommerce.coresport.service.implementation;

import com.ecommerce.coresport.entity.Product;
import com.ecommerce.coresport.exception.ProductNotFoundException;
import com.ecommerce.coresport.mapper.ProductMapper;
import com.ecommerce.coresport.model.ProductResponse;
import com.ecommerce.coresport.repository.ProductRepository;
import com.ecommerce.coresport.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;

    @Override
    public ProductResponse getProductById(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product not found!")
        );
        return productMapper.toProductResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponse)
                .toList();
    }
}

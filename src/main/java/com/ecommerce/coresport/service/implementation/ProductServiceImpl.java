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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
    public Page<ProductResponse> getAllProducts(Pageable pageable, Integer brandId, Integer typeId, String keyword) {
        Specification<Product> spec = null;

        if (brandId != null) {
            spec = and(spec,
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("brand").get("id"), brandId)
            );
        }
        if (typeId != null) {
            spec = and(spec,
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type").get("id"), typeId)
            );
        }
        if (keyword != null && !keyword.isEmpty()) {
            spec = and(spec,
                (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + keyword + "%")
            );
        }
        return productRepository.findAll(spec, pageable).map(productMapper::toProductResponse);
    }

    private Specification<Product> and(Specification<Product> base, Specification<Product> addition) {
        return base == null ? addition : base.and(addition);
    }

}

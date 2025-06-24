package com.ecommerce.coresport.mapper;

import com.ecommerce.coresport.entity.Product;
import com.ecommerce.coresport.model.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "brand.name", target = "productBrand")
    @Mapping(source = "type.name", target = "productType")
    ProductResponse toProductResponse(Product product);
}

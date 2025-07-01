package com.ecommerce.coresport.mapper;

import com.ecommerce.coresport.entity.Brand;
import com.ecommerce.coresport.model.BrandResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandResponse toBrandResponse(Brand brand);
}

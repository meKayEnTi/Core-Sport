package com.ecommerce.coresport.mapper;

import com.ecommerce.coresport.entity.Type;
import com.ecommerce.coresport.model.TypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeMapper {
    public TypeResponse toTypeResponse(Type type);
}

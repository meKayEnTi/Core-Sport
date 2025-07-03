package com.ecommerce.coresport.mapper;

import com.ecommerce.coresport.entity.User;
import com.ecommerce.coresport.model.RegisterRequest;
import com.ecommerce.coresport.model.UserResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(RegisterRequest registerRequest);
}

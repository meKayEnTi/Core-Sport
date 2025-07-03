package com.ecommerce.coresport.mapper;

import com.ecommerce.coresport.entity.Role;
import com.ecommerce.coresport.model.RoleResponse;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toRoleResponse(Role role);

    Set<RoleResponse> toRoleResponse(Set<Role> roles);
}

package com.ecommerce.coresport.repository;

import com.ecommerce.coresport.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}

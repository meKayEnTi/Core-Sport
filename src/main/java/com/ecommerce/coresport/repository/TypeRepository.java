package com.ecommerce.coresport.repository;

import com.ecommerce.coresport.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

    // Additional query methods can be defined here if needed
}

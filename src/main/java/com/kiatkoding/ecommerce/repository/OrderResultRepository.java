package com.kiatkoding.ecommerce.repository;

import com.kiatkoding.ecommerce.model.entity.OrderResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderResultRepository extends JpaRepository<OrderResultEntity, Integer> {
}

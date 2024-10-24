package com.kiatkoding.ecommerce.repository;

import com.kiatkoding.ecommerce.model.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Integer> {
}

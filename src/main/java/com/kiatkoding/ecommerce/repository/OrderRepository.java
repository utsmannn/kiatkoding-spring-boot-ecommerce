package com.kiatkoding.ecommerce.repository;

import com.kiatkoding.ecommerce.model.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    Page<OrderEntity> findAllByUserId(Integer userId, Pageable pageable);
}

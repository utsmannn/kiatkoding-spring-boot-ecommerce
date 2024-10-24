package com.kiatkoding.ecommerce.repository;

import com.kiatkoding.ecommerce.model.entity.CartEntity;
import com.kiatkoding.ecommerce.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

    @Query("""
            SELECT cart FROM CartEntity cart WHERE
            cart.userId = :userId
            ORDER BY cart.createdAtMillis DESC
            """)
    List<CartEntity> filter(Integer userId);

    @Query("""
            SELECT cart FROM CartEntity cart WHERE
            cart.userId = :userId AND cart.product.id = :productId
            """)
    Optional<CartEntity> getFilter(Integer userId, Integer productId);

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM CartEntity cart WHERE
            cart.userId = :userId AND cart.product.id IN :ids
            """)
    void deleteCartByUserIdAndProductIds(Integer userId, List<Integer> ids);
}

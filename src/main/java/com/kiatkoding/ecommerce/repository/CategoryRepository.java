package com.kiatkoding.ecommerce.repository;

import com.kiatkoding.ecommerce.model.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    Page<CategoryEntity> findAll(Pageable pageable);
}

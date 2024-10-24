package com.kiatkoding.ecommerce.service;

import com.kiatkoding.ecommerce.model.entity.CategoryEntity;
import com.kiatkoding.ecommerce.model.response.PagingInfo;
import com.kiatkoding.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public PagingInfo<CategoryEntity> getCategories(
            Integer pageNumber,
            Integer pageSize
    ) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        Page<CategoryEntity> categories = categoryRepository.findAll(pageRequest);

        return PagingInfo.convertFromPage(categories);
    }

}

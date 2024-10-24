package com.kiatkoding.ecommerce.controller;

import com.kiatkoding.ecommerce.model.entity.CategoryEntity;
import com.kiatkoding.ecommerce.model.response.BaseResponse;
import com.kiatkoding.ecommerce.model.response.PagingInfo;
import com.kiatkoding.ecommerce.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController {

    public final CategoryService categoryService;

    @GetMapping
    public BaseResponse getCategories(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {

        PagingInfo<CategoryEntity> categoriesPage = categoryService.getCategories(page, size);
        return new BaseResponse(true, "Success", categoriesPage);
    }
}

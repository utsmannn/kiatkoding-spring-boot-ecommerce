package com.kiatkoding.ecommerce.service;

import com.kiatkoding.ecommerce.model.ProductSort;
import com.kiatkoding.ecommerce.model.entity.ProductEntity;
import com.kiatkoding.ecommerce.model.response.PagingInfo;
import com.kiatkoding.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public PagingInfo<ProductEntity> getProducts(
            Integer pageNumber,
            Integer pageSize,
            String sort
    ) {

        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);

        if (sort != null) {
            pageRequest = pageRequest.withSort(mapStringToSort(sort));
        }

        Page<ProductEntity> products = productRepository.findAll(pageRequest);

        return PagingInfo.convertFromPage(products);
    }

    public PagingInfo<ProductEntity> getProducts(
            Integer pageNumber,
            Integer pageSize,
            String query,
            String sort
    ) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);

        if (sort != null) {
            pageRequest = pageRequest.withSort(mapStringToSort(sort));
        }

        Page<ProductEntity> products = productRepository
                .filter(query, pageRequest);

        return PagingInfo.convertFromPage(products);
    }

    public PagingInfo<ProductEntity> getProducts(
            Integer pageNumber,
            Integer pageSize,
            String query,
            Integer categoryId,
            String sort
    ) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);

        if (sort != null) {
            pageRequest = pageRequest.withSort(mapStringToSort(sort));
        }

        Page<ProductEntity> products = productRepository
                .filter(query, categoryId, pageRequest);

        return PagingInfo.convertFromPage(products);
    }

    public PagingInfo<ProductEntity> getProducts(
            Integer pageNumber,
            Integer pageSize,
            Integer categoryId,
            String sort
    ) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);

        if (sort != null) {
            pageRequest = pageRequest.withSort(mapStringToSort(sort));
        }

        Page<ProductEntity> products = productRepository
                .filter(categoryId, pageRequest);

        return PagingInfo.convertFromPage(products);
    }

    private Sort mapStringToSort(String value) {
        ProductSort productSort = ProductSort.fromValue(value);
        return switch (productSort) {
            case PRICE_ASCENDING -> Sort.by("price").ascending();
            case PRICE_DESCENDING -> Sort.by("price").descending();
        };
    }
}

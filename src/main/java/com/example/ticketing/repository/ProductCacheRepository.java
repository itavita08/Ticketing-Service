package com.example.ticketing.repository;

import com.example.ticketing.exception.ErrorCode;
import com.example.ticketing.exception.TicketingApplicationException;
import com.example.ticketing.model.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductCacheRepository {

    private final ProductRepository productRepository;

    @Cacheable(cacheNames = "product", key = "#productId", value = "product")
    public ProductEntity loadProduct(int productId){
        return productRepository.findByProductId(productId).orElseThrow(() ->
                new TicketingApplicationException(ErrorCode.PRODUCT_NOT_FOUND));
    }
}

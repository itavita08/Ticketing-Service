package com.example.ticketing.controller.response;

import com.example.ticketing.model.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Getter
public class ProductResponse {

    private String productName;
    @JsonFormat(pattern = "yyyy-MM-dd") private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd") private LocalDate endDate;
    private String viewingAge;
    private String stadiumName;
    private Integer price;

    public static ProductResponse fromProduct(Product product){
        return ProductResponse.builder()
                .productName(product.getProductName())
                .startDate(product.getStartDate())
                .endDate(product.getEndDate())
                .viewingAge(product.getViewingAge())
                .stadiumName(product.getStadiumName())
                .price(product.getPrice())
                .build();
    }
}

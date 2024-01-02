package com.example.ticketing.model;

import com.example.ticketing.model.entity.ProductEntity;
import com.example.ticketing.model.entity.StadiumEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Builder
public class Product {

    private Integer id;
    private String productName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String viewingAge;
    private String stadiumName;
    private Integer leftTicket;
    private Integer price;

    public static Product fromEntity(ProductEntity entity){
        return Product.builder()
                .id(entity.getId())
                .productName(entity.getProductName())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .viewingAge(entity.getViewingAge().getMessage())
                .stadiumName(entity.getStadium().getStadiumName())
                .leftTicket(entity.getLeftTicket())
                .price(entity.getPrice())
                .build();
    }
}

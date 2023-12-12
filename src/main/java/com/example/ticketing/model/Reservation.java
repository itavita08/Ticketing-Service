package com.example.ticketing.model;

import com.example.ticketing.model.entity.ReservationEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class Reservation {

    private Integer id;
    private String userName;
    private String productName;
    private LocalDate startDate;
    private LocalDate endDate;

    public static Reservation fromEntity(ReservationEntity entity){
        return Reservation.builder()
                .id(entity.getId())
                .userName(entity.getUser().getUserName())
                .productName(entity.getProduct().getProductName())
                .startDate(entity.getProduct().getStartDate())
                .endDate(entity.getProduct().getEndDate())
                .build();
    }

}

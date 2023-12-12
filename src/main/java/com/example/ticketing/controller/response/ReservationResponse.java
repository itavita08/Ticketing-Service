package com.example.ticketing.controller.response;

import com.example.ticketing.model.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReservationResponse {

    private Integer id;
    private String userName;
    private String productName;
    @JsonFormat(pattern = "yyyy-MM-dd") private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd") private LocalDate endDate;

    public static ReservationResponse fromReservation(Reservation reservation){
        return ReservationResponse.builder()
                .id(reservation.getId())
                .userName(reservation.getUserName())
                .productName(reservation.getProductName())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .build();
    }
}

package com.example.ticketing.controller;

import com.example.ticketing.exception.ErrorCode;
import com.example.ticketing.exception.TicketingApplicationException;
import com.example.ticketing.model.Reservation;
import com.example.ticketing.model.ViewingAge;
import com.example.ticketing.model.entity.ProductEntity;
import com.example.ticketing.model.entity.StadiumEntity;
import com.example.ticketing.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;
    @MockBean
    private Reservation reservation;

    @DisplayName("예매 가능한 상품 리스트 조회")
    @Test
    void given_whenSearch_thenProductList() throws Exception {

        when(reservationService.productList()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("예매 중복")
    @Test
    void givenUserAndProduct_whenReservation_thenFail() throws Exception {
        String userName = "test";

        doThrow(new TicketingApplicationException(ErrorCode.DUPLICATED_RESERVATION))
                .when(reservationService).reserve(userName, 1);

        mockMvc.perform(post("/api/v1/book/{productId}", 1)
                        .queryParam("name", userName))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @DisplayName("매진")
    @Test
    void givenUserAndProduct_whenReservation_thenSoldOut() throws Exception {
        String userName = "test";

        doThrow(new TicketingApplicationException(ErrorCode.SOLD_OUT))
                .when(reservationService).reserve(userName, 1);

        mockMvc.perform(post("/api/v1/book/{productId}", 1)
                        .queryParam("name", userName))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("예매 성공")
    @Test
    void givenUserAndProduct_whenReservation_thenSuccess() throws Exception {
        String userName = "test";
        Integer productId = 1;

        when(reservationService.reserve(userName, productId)).thenReturn(reservation);

        mockMvc.perform(post("/api/v1/book/{productId}", 1)
                        .queryParam("name", userName))
                .andDo(print())
                .andExpect(status().isOk());
    }

}

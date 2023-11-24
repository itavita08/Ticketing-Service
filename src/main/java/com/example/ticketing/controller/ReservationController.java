package com.example.ticketing.controller;

import com.example.ticketing.controller.response.ProductResponse;
import com.example.ticketing.controller.response.ReservationResponse;
import com.example.ticketing.controller.response.Response;
import com.example.ticketing.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public Response<List<ProductResponse>> productList(){

        return Response.success(reservationService.productList().stream().map(ProductResponse::fromProduct).toList());
    }

    @PostMapping("/{productId}")
    public Response<ReservationResponse> reserve(@RequestParam String name, @PathVariable Integer productId){

        return Response.success(ReservationResponse.fromReservation(reservationService.reserve(name, productId)));
    }
}

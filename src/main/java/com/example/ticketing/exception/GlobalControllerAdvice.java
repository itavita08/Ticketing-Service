package com.example.ticketing.exception;

import com.example.ticketing.controller.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(TicketingApplicationException.class)
    public ResponseEntity<?> applicationHandler(TicketingApplicationException e){
        log.error("Error occurs {}", e.toString());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Response.error(e.getErrorCode().name()));
    }
}

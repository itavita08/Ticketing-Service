package com.example.ticketing.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_USER(HttpStatus.CONFLICT, "User name is duplicated"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password is invalid"),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "Product not founded"),
    SOLD_OUT(HttpStatus.NOT_ACCEPTABLE, "Product sold out"),
    DUPLICATED_RESERVATION(HttpStatus.CONFLICT, "Reservation is duplicated");

    private final HttpStatus httpStatus;
    private final String message;
}

package com.example.ticketing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ViewingAge {

    ALL_AGE("전 연령 관람 가능"),
    TWELVE_YEARS_OR_OLDER("12세 이상 관람 가능"),
    FIFTEEN_YEARS_OR_OLDER("15세 이상 관람 가능"),
    YOUTH_NOT_ALLOWED("청소년 관람 불가");

    private String message;
}

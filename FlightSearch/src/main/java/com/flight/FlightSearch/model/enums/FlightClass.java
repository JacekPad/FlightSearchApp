package com.flight.FlightSearch.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum FlightClass {

    ECONOMY("ECONOMY", "Economy"),
    BUSINESS("BUSINESS", "Business"),
    FIRST("FIRST", "First");

    private final String code;
    private final String value;

    FlightClass(String code, String value) {
        this.code = code;
        this.value = value;
    }
}

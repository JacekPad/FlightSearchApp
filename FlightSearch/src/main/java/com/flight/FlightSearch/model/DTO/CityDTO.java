package com.flight.FlightSearch.model.DTO;

import lombok.Data;

@Data
public class CityDTO {

    String id;
    String code;
    String name;
    CountryDTO country;
}

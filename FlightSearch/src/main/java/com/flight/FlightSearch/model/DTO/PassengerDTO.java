package com.flight.FlightSearch.model.DTO;

import com.flight.FlightSearch.model.enums.PassengerEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PassengerDTO {

    private PassengerEnum type;
    private Integer quantity;

}

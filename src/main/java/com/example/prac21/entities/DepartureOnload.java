package com.example.prac21.entities;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class DepartureOnload {
    private String type;
    private String departureDate;
    private Long officeId;
}

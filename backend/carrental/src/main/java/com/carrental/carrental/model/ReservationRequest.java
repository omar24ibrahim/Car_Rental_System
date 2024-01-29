package com.carrental.carrental.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequest {
    Date startDate;
    Date endDate;
    Long plateId;
    String email;
}


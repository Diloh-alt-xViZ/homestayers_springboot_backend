package com.developer.homestayersbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalBookingRequest {

    private int numberOfGuests;
    private Long propertyId;
    private Long guestId;
    private Date startDate;
    private Double price;

}

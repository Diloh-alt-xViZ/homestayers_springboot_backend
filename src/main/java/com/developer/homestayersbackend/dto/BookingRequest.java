package com.developer.homestayersbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {


    private Long roomId;
    private Long numberOfGuests;
    private Long propertyId;
    private Long guestId;
    private Date startDate;
    private Date endDate;
    private Double price;

}

package com.developer.homestayersbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    private String status;
    private Long bookingId;
    private String guestName;
    private String fromDate;
    private String toDate;
    private String propertyName;
    private Long guestCount;
    private String guestAvatar;
}

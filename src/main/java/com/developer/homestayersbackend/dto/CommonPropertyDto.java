package com.developer.homestayersbackend.dto;


import com.developer.homestayersbackend.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonPropertyDto {

    private List<CalendarRoomDto> rooms;
    private List<PhotoDto> photos;
    private Location location;
    private Long id;
    private String title;
    private String approvalStatus;
    private Date startDate;
    private String listingType;
    private List<String> bookedDates;

}

package com.developer.homestayersbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarRoomDto {

    private String title;
    private List<String> bookedDates;

}

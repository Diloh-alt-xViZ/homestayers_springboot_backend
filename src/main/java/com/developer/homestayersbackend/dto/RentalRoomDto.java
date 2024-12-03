package com.developer.homestayersbackend.dto;

import com.developer.homestayersbackend.entity.RoomType;
import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalRoomDto {

    /*
        Valid Room Types include("Bedroom","Kitchen","Bathroom","Living Room","Dining")
     */

    private String roomType;
}

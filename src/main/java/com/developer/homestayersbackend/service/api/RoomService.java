package com.developer.homestayersbackend.service.api;

import com.developer.homestayersbackend.dto.RoomRequest;
import com.developer.homestayersbackend.dto.RoomResponseDto;
import com.developer.homestayersbackend.entity.Room;

import java.util.List;

public interface RoomService {

    public Room editRoom(RoomRequest request);

    List<RoomResponseDto> getPropertyRooms(Long propertyId);
}

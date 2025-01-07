package com.developer.homestayersbackend.controller;

import com.developer.homestayersbackend.dto.RoomRequest;
import com.developer.homestayersbackend.dto.RoomResponseDto;
import com.developer.homestayersbackend.entity.Room;
import com.developer.homestayersbackend.service.api.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/property/{propertyId}/rooms/editRoom")
    public ResponseEntity<Room> editRoom(@PathVariable Long propertyId, @RequestBody RoomRequest room) {

        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.editRoom(room));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/property/{propertyId}/getRooms")
    public ResponseEntity<List<RoomResponseDto>> getRooms(@PathVariable Long propertyId) {

        return ResponseEntity.ok(roomService.getPropertyRooms(propertyId));
    }

}

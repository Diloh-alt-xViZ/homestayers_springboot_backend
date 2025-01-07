package com.developer.homestayersbackend.controller;


import com.developer.homestayersbackend.dto.PhotoDeleteDto;
import com.developer.homestayersbackend.service.api.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/property/photos")
public class PhotoController {

    private final PhotoService photoService;

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/rooms/deletePhotos")
    public ResponseEntity<String> deletePhotos(@RequestBody PhotoDeleteDto dto) {

        return ResponseEntity.ok(photoService.deleteRoomPhotos(dto));
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/rooms/deleteMultiplePhotos")
    public ResponseEntity<String> deleteMultiplePhotos(@RequestBody List<PhotoDeleteDto> dto) {


        return ResponseEntity.ok(photoService.deleteMultiplePhotos(dto));
    }

}

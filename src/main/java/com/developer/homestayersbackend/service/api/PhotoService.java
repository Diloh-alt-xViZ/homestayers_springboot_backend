package com.developer.homestayersbackend.service.api;

import com.developer.homestayersbackend.dto.PhotoDeleteDto;
import com.developer.homestayersbackend.dto.PhotoDto;
import com.developer.homestayersbackend.entity.Photo;

import java.util.List;

public interface PhotoService {
    Photo createPhoto(Photo photo);

    String deleteRoomPhotos(PhotoDeleteDto dto);

    String deleteMultiplePhotos(List<PhotoDeleteDto> dto);

    PhotoDto addUserProfilePhoto(PhotoDto dto, Long userId);
}


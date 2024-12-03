package com.developer.homestayersbackend.service.impl;

import com.developer.homestayersbackend.entity.Photo;
import com.developer.homestayersbackend.repository.PhotoRepository;
import com.developer.homestayersbackend.service.api.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    @Override
    public Photo createPhoto(Photo photo) {
        return photoRepository.save(photo);
    }
}

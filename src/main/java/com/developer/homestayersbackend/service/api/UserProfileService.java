package com.developer.homestayersbackend.service.api;

import com.developer.homestayersbackend.dto.UserProfileDto;
import com.developer.homestayersbackend.entity.UserProfile;

public interface UserProfileService {
    UserProfile createProfile(UserProfile userProfile);

    UserProfileDto getProfile(Long userId);
}

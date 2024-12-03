package com.developer.homestayersbackend.service.impl;

import com.developer.homestayersbackend.dto.PhotoDto;
import com.developer.homestayersbackend.dto.UserProfileDto;
import com.developer.homestayersbackend.entity.Address;
import com.developer.homestayersbackend.entity.Gender;
import com.developer.homestayersbackend.entity.User;
import com.developer.homestayersbackend.entity.UserProfile;
import com.developer.homestayersbackend.exception.UserNotFoundException;
import com.developer.homestayersbackend.repository.UserProfileRepository;
import com.developer.homestayersbackend.repository.UserRepository;
import com.developer.homestayersbackend.service.api.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;


    @Override
    public UserProfileDto getProfile(Long userId) {
        System.out.println("Inside Profile Service");
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new UserNotFoundException();
        }
        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(userId);
        if(userProfile!=null) {
            return getUserProfileDto(userProfile);

        }
        return null;
    }

    private static UserProfileDto getUserProfileDto(UserProfile userProfileEntity) {
        UserProfileDto userProfileDto = new UserProfileDto();


        if(userProfileEntity.getAddress()!=null) {
            Address address = new Address();
            userProfileDto.setAddress(userProfileEntity.getAddress().getAddress());
            userProfileDto.setCountry(userProfileEntity.getAddress().getCountry());
            userProfileDto.setState(userProfileEntity.getAddress().getState());
            userProfileDto.setStreet(userProfileEntity.getAddress().getStreet());
            userProfileDto.setCity(userProfileEntity.getAddress().getCity());
        }

        if(userProfileEntity.getPhoto()!=null){
            userProfileDto.setProfilePhoto(new PhotoDto(userProfileEntity.getPhoto().getUrl()));
        }
        if(userProfileEntity.getGender()!=null){
            userProfileDto.setGender(userProfileEntity.getGender().getName());
        }
        userProfileDto.setBio(userProfileEntity.getBio());
        userProfileDto.setIdNumber(userProfileEntity.getNationalId());
        userProfileDto.setFirstName(userProfileEntity.getFirstName());
        userProfileDto.setLastName(userProfileEntity.getLastName());
        userProfileDto.setDob(userProfileEntity.getDateOfBirth());
        userProfileDto.setIntro(userProfileDto.getIntro());
        userProfileDto.setWork(userProfileEntity.getWork());
        userProfileDto.setSchool(userProfileEntity.getSchool());
        return userProfileDto;
    }

    @Override
    public UserProfile createProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }
}

package com.developer.homestayersbackend.service;

import com.developer.homestayersbackend.dto.GoogleOauth2UserInfo;
import com.developer.homestayersbackend.entity.Photo;
import com.developer.homestayersbackend.entity.Role;
import com.developer.homestayersbackend.entity.User;
import com.developer.homestayersbackend.entity.UserProfile;
import com.developer.homestayersbackend.repository.PhotoRepository;
import com.developer.homestayersbackend.repository.UserProfileRepository;
import com.developer.homestayersbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final PhotoRepository photoRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        Map attributes = (Map) oidcUser.getAttributes();

        GoogleOauth2UserInfo userInfo = new GoogleOauth2UserInfo();

        userInfo.setEmail(attributes.get("email").toString());
        userInfo.setId(attributes.get("sub").toString());
        userInfo.setName(attributes.get("name").toString());
        userInfo.setImageUrl(attributes.get("picture").toString());
        updateUserInfo(userInfo);
        return oidcUser;

    }

    private void updateUserInfo(GoogleOauth2UserInfo userInfo) {
        Optional<User> user = userRepository.findByEmail(userInfo.getEmail());
        UserProfile userProfile = new UserProfile();
        Photo photo = new Photo();
        User newUser = new User();
        if(user.isEmpty()){
            newUser.setEmail(userInfo.getEmail());
            newUser.setUsername(userInfo.getEmail());
            newUser.setRole(Role.USER);
            newUser.setPassword(passwordEncoder.encode("Password"));
            newUser.setDateRegistered(new Date(System.currentTimeMillis()));
            System.out.println("Saving user");
            User dbUser = userRepository.save(newUser);
            String[] fullName = userInfo.getName().split(" ");
            photo.setUrl(userInfo.getImageUrl());
            var dbPhoto = photoRepository.save(photo);
            userProfile.setFirstName(fullName[0]);
            userProfile.setUser(dbUser);
            userProfile.setLastName(fullName[1]);
            userProfile.setPhoto(dbPhoto);
            userProfileRepository.save(userProfile);
        }
        else {
            System.out.println("User already exists");
        }
    }


}

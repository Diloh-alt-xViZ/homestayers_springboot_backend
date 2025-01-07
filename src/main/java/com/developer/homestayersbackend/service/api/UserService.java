package com.developer.homestayersbackend.service.api;

import com.developer.homestayersbackend.controller.auth.AuthenticationResponse;
import com.developer.homestayersbackend.dto.*;
import com.developer.homestayersbackend.entity.Host;
import com.developer.homestayersbackend.entity.User;
import com.developer.homestayersbackend.entity.UserProfile;
import com.developer.homestayersbackend.exception.UserNotFoundException;
import com.developer.homestayersbackend.util.RegistrationStatus;

import java.util.List;
import java.util.Optional;

public interface UserService {
    RegistrationStatus attemptLogin(String email) throws UserNotFoundException;
    Optional<User> validateUserByEmail(String email);
    //AuthenticationResponse registerUser(RegistrationRequest user);

    //AuthenticationResponse loginUser(AuthenticationRequest request);
    List<User> getAllUsers();
    User getUserById(Long id);
    UserDto getUserDetailsById(Long id);
    //Host verifyHost(Long hostId);
    //AuthenticationResponse registerUserByEmail(String email, String password);
    UserProfile editProfile(Long userId, UserProfileDto userProfileDto);

    AuthenticationResponse login(String email, String password);

    boolean verifyUserEmail(String token);
    Host createHost(Long userId);
    List<Host> getAllHosts(String verificationStatus);
    //AuthenticationResponse login(String email, String password);
    void updateUser(User user);

    RegistrationStatus registerNewEmail(String email);

    AuthenticationResponse createNewUserProfileAndAuthenticate(UserProfileDetailsDto userDetails);

    void verifyUser(Long userId);

    RefreshTokenDto getNewToken(RefreshTokenRequest request);

    Host getHost(Long userId);

    Long createNewHost(Long userId);

    AuthenticationResponse createNewUserFromOidc(OidcRequest oidcRequest);

    AuthenticationResponse createNewPhoneUserAndAuthenticate(PhoneUserProfileDetailsDto dto);

    boolean checkUserDetails(Long id);

    AuthenticationResponse loginPhoneUser(PhoneLoginRequest req);
}

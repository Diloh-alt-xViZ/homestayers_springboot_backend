package com.developer.homestayersbackend.service.impl;

import com.developer.homestayersbackend.controller.auth.AuthenticationResponse;
import com.developer.homestayersbackend.dto.*;
import com.developer.homestayersbackend.entity.*;
import com.developer.homestayersbackend.exception.*;
import com.developer.homestayersbackend.repository.*;
import com.developer.homestayersbackend.service.JwtService;
import com.developer.homestayersbackend.service.api.EmailService;
import com.developer.homestayersbackend.service.api.UserService;
import com.developer.homestayersbackend.util.EmailVerification;
import com.developer.homestayersbackend.util.PhoneNumberUtils;
import com.developer.homestayersbackend.util.RegistrationStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;
    private final ModelMapper profileModelMapper;
    private final PhotoRepository photoRepository;
    private final HostRepository hostRepository;
    private final JavaMailSender javaMailSender;
    private final EmailService emailService;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;

    private Gender getGender(String gender){

        Gender userGender;

        userGender = switch(gender){

            case "Male"-> Gender.MALE;
            case "Female"-> Gender.FEMALE;
            case "Other"-> Gender.OTHER;
            default->null;
        };
        return userGender;
    }

    @Override
    public RefreshTokenDto getNewToken(RefreshTokenRequest request) {
        System.out.println("Inside Get New Token");
        String username = jwtService.extractUsername(request.getRefreshToken());

        Optional<User> userDetails = userRepository.findByUsername(username);
        if(userDetails.isPresent()){

            String token = jwtService.generateToken(userDetails.get());
            String refreshToken = jwtService.generateRefreshToken(userDetails.get());
            RefreshTokenDto refreshTokenDto = new RefreshTokenDto();
            refreshTokenDto.setRefreshToken(refreshToken);
            refreshTokenDto.setToken(token);
            return refreshTokenDto;
        }

        else throw new UserNotFoundException();
    }

    @Override
    public AuthenticationResponse createNewUserFromOidc(OidcRequest oidcRequest) {

        if(oidcRequest.getEmail()!=null){
            Optional<User> user = userRepository.findByEmail(oidcRequest.getEmail());
            if(user.isPresent()){
                return getAuthenticationResponse(user.get());
            }
            else{
                User newUser = createNewOidcUser(oidcRequest);
                return getAuthenticationResponse(newUser);
            }
        }

        if(oidcRequest.getPhoneNumber()!=null){
            Optional<User> user = userRepository.findByUsername(oidcRequest.getPhoneNumber());
            if(user.isPresent()){
                return getAuthenticationResponse(user.get());
            }
            else {
                User newUser = createNewOidcPhoneUser(oidcRequest);
                return getAuthenticationResponse(newUser);
            }

        }

        return null;
    }

    private User createNewOidcPhoneUser(OidcRequest oidcRequest) {
        User user = new User();

        if(oidcRequest.getPhoneNumber()!=null){
            PhoneNumber phoneNumber = PhoneNumberUtils.getPhoneNumber(oidcRequest.getPhoneNumber());
            user.setPhoneNumber(phoneNumber);
            user.setVerificationStatus(VerificationStatus.VERIFIED);
            user.setUsername(phoneNumber.getCountryCode()+phoneNumber.getNumber());
        }
        UserProfile userProfile = new UserProfile();
        if(oidcRequest.getFirstName()!=null){
            userProfile.setFirstName(oidcRequest.getFirstName());
        }
        if(oidcRequest.getLastName()!=null){
            userProfile.setLastName(oidcRequest.getLastName());
        }
        if(oidcRequest.getProfilePicture()!=null){
            Photo photo = new Photo();
            photo.setUrl(oidcRequest.getProfilePicture().getUri());
            userProfile.setPhoto(photoRepository.save(photo));
        }
        user.setRole(Role.USER);
        user.setDateRegistered(new Date(System.currentTimeMillis()));
        User dbUser = userRepository.save(user);
        userProfile.setUser(dbUser);
        userProfileRepository.save(userProfile);
        return userRepository.save(user);
    }

    private User createNewOidcUser(OidcRequest oidcRequest) {
        User user = new User();
        UserProfile userProfile = new UserProfile();
        user.setUsername(oidcRequest.getEmail());
        user.setEmail(oidcRequest.getEmail());
        user.setDateRegistered(new Date(System.currentTimeMillis()));
        user.setEmailVerification(EmailVerification.VERIFIED);
        user.setVerificationStatus(VerificationStatus.VERIFIED);
        user.setRole(Role.USER);
        if(oidcRequest.getProfilePicture()!=null){
            Photo photo = new Photo();
            photo.setUrl(oidcRequest.getProfilePicture().getUri());
            userProfile.setPhoto(photoRepository.save(photo));
        }
        if(oidcRequest.getFirstName()!=null){
            userProfile.setFirstName(oidcRequest.getFirstName());
        }
        if(oidcRequest.getLastName()!=null){
            userProfile.setLastName(oidcRequest.getLastName());
        }
        User dbUser = userRepository.save(user);
        userProfile.setUser(dbUser);
        userProfileRepository.save(userProfile);
        return userRepository.save(user);
    }

    private AuthenticationResponse getAuthenticationResponse(User user) {
        return getAuthenticationResponse(user, jwtService);
    }

    static AuthenticationResponse getAuthenticationResponse(User user, JwtService jwtService) {
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(token);
        authenticationResponse.setRefreshToken(refreshToken);
        authenticationResponse.setUserId(user.getId());
        return authenticationResponse;
    }

    @Override
    public AuthenticationResponse createNewPhoneUserAndAuthenticate(PhoneUserProfileDetailsDto dto) {

        User dbUser = userRepository.findByUsername(dto.getPhoneNumber()).orElseThrow(UserNotFoundException::new);

        UserProfile userProfile = getUserProfile(dto, dbUser);
        userProfileRepository.save(userProfile);
        User savedUser = userRepository.save(dbUser);
        String token  = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);
        return AuthenticationResponse.builder().refreshToken(refreshToken).userId(dbUser.getId()).token(token).build();
    }

    private static UserProfile getUserProfile(PhoneUserProfileDetailsDto dto, User dbUser) {
        UserProfile userProfile = new UserProfile();
        if(dto.getIdNumber()!=null){
            userProfile.setNationalId(dto.getIdNumber());
        }
        if(dto.getFirstName()!=null){
            userProfile.setFirstName(dto.getFirstName());
        }
        if(dto.getLastName()!=null){
            userProfile.setLastName(dto.getLastName());
        }
        if(dto.getDob()!=null){
            userProfile.setDateOfBirth(new Date(dto.getDob().getTime()));
        }
        userProfile.setUser(dbUser);
        return userProfile;
    }

    @Override
    public AuthenticationResponse createNewUserProfileAndAuthenticate(UserProfileDetailsDto userDetails) {

        User  dbUser = new User();
        Optional<User> user = userRepository.findByEmail(userDetails.getEmail());
        UserProfile userProfile = new UserProfile();
        if (user.isPresent()) {
                dbUser = user.get();
                dbUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            System.out.println("We have a winner");
                   userProfile.setNationalId(userDetails.getIdNumber());

                userProfile.setFirstName(userDetails.getFirstName());
                userProfile.setLastName(userDetails.getLastName());
                if(userDetails.getDob()!=null){
                    userProfile.setDateOfBirth(new Date(userDetails.getDob().getTime()));
                }
                userProfile.setUser(dbUser);
                userProfileRepository.save(userProfile);
                getVerificationToken(dbUser);
                User savedUser = userRepository.save(dbUser);
                String token = jwtService.generateToken(savedUser);
                String refreshToken = jwtService.generateRefreshToken(savedUser);
                return AuthenticationResponse.builder().refreshToken(refreshToken).userId(dbUser.getId()).token(token).build();
        }
        else throw new UserNotFoundException();
    }

    private static Gender getGender(UserProfileDetailsDto userDetails) {
        Gender gender = null;
        if(userDetails.getGender()!=null){
            gender = switch (userDetails.getGender()){

                case "Female"-> Gender.FEMALE;
                case "Male"-> Gender.MALE;
                case "Other"-> Gender.OTHER;
                default -> throw new IllegalStateException("Unexpected value: " + userDetails.getGender());
            };
        }
        return gender;
    }

    private void getVerificationToken(User user){
        createEmailToken(user, user);
    }

    @Override
    public RegistrationStatus registerNewEmail(String email) {
        User user = new User();
        user.setEmail(email);
        user.setRole(Role.USER);
        user.setUsername(email);
        user.setDateRegistered(new Date(System.currentTimeMillis()));
        user.setVerificationStatus(VerificationStatus.PENDING);
        user.setEmailVerification(EmailVerification.PENDING);
        User dbUser = userRepository.save(user);
        //TODO:SEND VERIFICATION EMAIL
        createEmailToken(user, dbUser);
        return RegistrationStatus.INCOMPLETE;
    }

    private void createEmailToken(User user, User dbUser) {
        String emailToken  = UUID.randomUUID().toString();
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken(emailToken);
        emailVerificationToken.setExpiryDate(LocalDateTime.now().plusHours(5));
        emailVerificationToken.setUser(dbUser);
        emailVerificationTokenRepository.save(emailVerificationToken);
        emailService.sendVerificationEmail(user.getEmail(),emailToken);
    }

    @Override
    public RegistrationStatus attemptLogin(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            System.out.println("Found thy user");
            if(checkRegistrationCompletion(user.get())){
                return RegistrationStatus.COMPLETE;
            }
            else return RegistrationStatus.INCOMPLETE;
        }

        else throw new UserNotFoundException();
    }

  @Override
    public AuthenticationResponse login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        if (passwordEncoder.matches(password, user.get().getPassword())) {
            return getAuthenticationResponse(user.get());
        }

        else{
         throw new IllegalArgumentException("Invalid email password combination");
        }
    }

    private boolean checkTokenExpiration(EmailVerificationToken token){

        return LocalDateTime.now().isBefore(token.getExpiryDate());
    }

    @Override
    public boolean verifyUserEmail(String token) {
        EmailVerificationToken  emailToken = emailVerificationTokenRepository.findByToken(token).orElseThrow(()->{
            return new EmailVerificationTokenNotFoundException(token);
        });
        System.out.println(emailToken);
        User user = new User();
        System.out.println(emailToken);
        if(checkTokenExpiration(emailToken)){
            user = emailToken.getUser();
            System.out.println("User:" + user);
            user.setEmailVerification(EmailVerification.VERIFIED);
            user.setVerificationStatus(VerificationStatus.VERIFIED);
            System.out.println("Verification:"+ user.getEmailVerification());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private boolean checkRegistrationCompletion(User user){
        String password = user.getPassword();
        return password != null;
    }

    /*
    @Override
    public AuthenticationResponse registerUserByEmail(String email, String password) {
        Optional<User> dbUser = userRepository.findByEmail(email);
        if(dbUser.isPresent()) {
            if (checkRegistrationCompletion(dbUser.get())){

            }
            return login(email, password);
        }
            String emailToken  = UUID.randomUUID().toString();
            EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
            emailVerificationToken.setToken(emailToken);
            User user = new User();
            UserProfile userProfile = new UserProfile();
            user.setEmail(email);
            user.setUsername(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setDateRegistered(new Date(System.currentTimeMillis()));
            user.setRole(Role.USER);
            user.setEmailVerification(EmailVerification.PENDING);
            User savedUser = userRepository.save(user);
            emailVerificationToken.setExpiryDate(LocalDateTime.now().plusHours(5));
            emailVerificationToken.setUser(savedUser);
            emailVerificationTokenRepository.save(emailVerificationToken);
            userProfile.setUser(savedUser);
            userProfileRepository.save(userProfile);
            emailService.sendVerificationEmail(savedUser.getEmail(),emailToken);
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setToken(emailToken);
            authenticationResponse.setUserId(savedUser.getId());
            authenticationResponse.setAuthenticationStatus(AuthenticationStatus.GRANTED);
            return authenticationResponse;
    }
*/
    @Override
    public void updateUser(User user) {

        userRepository.save(user);
    }


    private UserDto sanitizeToDto(User user,UserProfile userProfile){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setUserId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(userProfile.getFirstName());
        userDto.setLastName(userProfile.getLastName());
        Photo photo = userProfile.getPhoto();
        if(photo!=null){
        userDto.setProfilePhotoUrl(photo.getUrl());
        }
        return userDto;
    }


    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public boolean checkUserDetails(Long id) {

        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(id);

        return userProfile != null;
    }

    @Override
    public UserDto getUserDetailsById(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(user.getId());
        if(userProfile==null){
            throw new UserProfileNotFoundException();
        }
        return sanitizeToDto(user,userProfile);
    }

    @Override
    public List<Host> getAllHosts(String verificationStatus) {

        if(verificationStatus.equalsIgnoreCase("verified")) {
            //return hostRepository.findBy(VerificationStatus.VERIFIED.toString());

        }

        if(verificationStatus.equalsIgnoreCase("pending")) {
            //return hostRepository.findByVerificationStatus(VerificationStatus.PENDING.toString());
        }

        else {
            return null;
        }
        return null;
    }


    @Override
    public Long createNewHost(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Optional<Host> host = hostRepository.findHostByUserId(user.getId());

        if(host.isPresent()){
            return host.get().getId();
        }
        if(user.getVerificationStatus()!= VerificationStatus.VERIFIED){
            throw new UserNotVerifiedException();
        }
        else {
            Host newHost = new Host();
            newHost.setUser(user);
            newHost.setHostSince(new Date(System.currentTimeMillis()));
            return hostRepository.save(newHost).getId();
        }
    }

    @Override
    public Host createHost(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Host> hostEntity = hostRepository.findHostByUserId(userId);
        Host host = new Host();
        return hostEntity.orElse(null);
    }

    @Override
    public Host getHost(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Host> hostEntity = hostRepository.findHostByUserId(userId);

        if(user.isPresent() ){
            if(hostEntity.isPresent()){
                System.out.println("Found Something");
                return hostEntity.get();
            }
            else {
                System.out.println("No host found");
                Host host = new Host();
                host.setHostSince(new Date(System.currentTimeMillis()));
                host.setUser(user.get());
                return hostRepository.save(host);
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public UserProfile editProfile(Long userId, UserProfileDto userProfileDto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(userId);
        if(userProfileDto.getProfilePhoto()!=null){
            Photo photo = photoRepository.findPhotoByUrl(userProfileDto.getProfilePhoto().getUri());
            Photo dbPhoto = new Photo();
            if(photo==null){
                dbPhoto = photoRepository.save(Photo.builder().url(userProfileDto.getProfilePhoto().getUri()).build());
                userProfile.setPhoto(dbPhoto);
            }
        }
        setProfileDetails(userProfile,userProfileDto);
        return userProfileRepository.save(userProfile);
    }

    private void setProfileDetails(UserProfile userProfile,UserProfileDto userProfileDto){
        userProfile.setWork(userProfileDto.getWork());
        userProfile.setSchool(userProfileDto.getSchool());
        userProfile.setIntro(userProfileDto.getIntro());
        userProfile.setBio(userProfileDto.getBio());
        userProfile.setAddress(Address.builder().address(userProfileDto.getAddress()).state(userProfileDto.getState()).city(userProfileDto.getCity()).country(userProfileDto.getCountry()).street(userProfileDto.getStreet()).build());
        userProfile.setDateOfBirth(userProfileDto.getDob());
        userProfile.setFirstName(userProfileDto.getFirstName());
        userProfile.setLastName(userProfileDto.getLastName());
        Gender gender = getGender(userProfileDto.getGender());
        userProfile.setGender(gender);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



    @Override
    public Optional<User> validateUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public void verifyUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            User userEntity = user.get();
            if(userEntity.getVerificationStatus()==VerificationStatus.VERIFIED){
                return;
            }
            userEntity.setVerificationStatus(VerificationStatus.VERIFIED);
            userRepository.save(userEntity);
        }
    }

    /*

    @Override
    public AuthenticationResponse loginUser(AuthenticationRequest request) {
        User dbUser = userRepository.findByEmail(request.getUsername()).orElseThrow(()->new UsernameNotFoundException("User Not Found"));

        var jwtToken = jwtService.generateToken(dbUser);
        return AuthenticationResponse.builder().token(jwtToken).userId(dbUser.getId()).build();
    }

     */

    /*
    @Override
    public AuthenticationResponse registerUser(RegistrationRequest user) {
        User sanitizedUser = sanitizeUser(user);
        var jwtToken = jwtService.generateToken(sanitizedUser);
        User savedUser = userRepository.save(sanitizedUser);
        return AuthenticationResponse.builder().token(jwtToken).userId(savedUser.getId()).build();
    }
    */


    private User sanitizeUser(RegistrationRequest user) {
        var dbUser = User
                .builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .dateRegistered(new Date(System.currentTimeMillis()))
                .role(Role.USER)
                .build();

        return dbUser;
    }
}

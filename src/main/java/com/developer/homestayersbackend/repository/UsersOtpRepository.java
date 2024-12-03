package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.dto.PhoneNumber;
import com.developer.homestayersbackend.entity.UsersOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersOtpRepository extends JpaRepository<UsersOtp, Long> {

    Optional<UsersOtp> findUsersByOtpAndPhoneNumber(String otp, PhoneNumber phoneNumber);

}

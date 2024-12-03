package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.PhoneVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneVerificationRepository extends JpaRepository<PhoneVerification, Long> {

    PhoneVerification findByPhoneNumber(String phoneNumber);
    Optional<PhoneVerification> findPhoneVerificationByPhoneNumberAndVerificationCode(String phoneNumber, String verificationCode);
}

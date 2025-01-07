package com.developer.homestayersbackend.service.impl;

import com.developer.homestayersbackend.dto.CardPaymentRequest;
import com.developer.homestayersbackend.dto.MobilePaymentRequest;
import com.developer.homestayersbackend.entity.CardPayment;
import com.developer.homestayersbackend.entity.MobilePayment;
import com.developer.homestayersbackend.entity.User;
import com.developer.homestayersbackend.exception.UserNotFoundException;
import com.developer.homestayersbackend.repository.UserRepository;
import com.developer.homestayersbackend.service.api.PaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentsServiceImpl implements PaymentsService {


    private final UserRepository userRepository;

    @Override
    public String makeCardPayment(CardPaymentRequest request, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        CardPayment cardPayment = new CardPayment();
        //USE THE PAYNOW API to make payment

        return null;
    }

    @Override
    public String makePhonePayment(MobilePaymentRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        MobilePayment payment = new MobilePayment();
        //USE THE PAYNOW API to make payment;
        return null;
    }
}

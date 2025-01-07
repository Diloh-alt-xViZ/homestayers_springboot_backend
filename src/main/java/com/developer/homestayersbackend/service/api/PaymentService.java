package com.developer.homestayersbackend.service.api;

import com.developer.homestayersbackend.dto.CardPaymentRequest;
import com.developer.homestayersbackend.dto.MobilePaymentRequest;

public interface PaymentsService {

    String makeCardPayment(CardPaymentRequest request, Long userId);
    String makePhonePayment(MobilePaymentRequest request, Long userId);
}

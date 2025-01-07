package com.developer.homestayersbackend.controller;


import com.developer.homestayersbackend.dto.CardPaymentRequest;
import com.developer.homestayersbackend.dto.MobilePaymentRequest;
import com.developer.homestayersbackend.entity.User;
import com.developer.homestayersbackend.service.api.PaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentsController {

    private final PaymentsService paymentsService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/card/pay")
    public ResponseEntity<String> makeCardPayment(@RequestBody CardPaymentRequest request,@AuthenticationPrincipal User user){

        Long userId = user.getId();

        return ResponseEntity.ok(paymentsService.makeCardPayment(request,userId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/mobile/pay")
    public ResponseEntity<String> makeMobilePayment(@RequestBody MobilePaymentRequest request,@AuthenticationPrincipal User user){

        Long userId = user.getId();
        return ResponseEntity.ok(paymentsService.makePhonePayment(request,userId));

    }
}

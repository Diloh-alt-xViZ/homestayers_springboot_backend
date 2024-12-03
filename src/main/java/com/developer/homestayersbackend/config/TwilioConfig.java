package com.developer.homestayersbackend.config;

import com.twilio.Twilio;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class TwilioConfig {

    @Value("${twilio.account.authToken}")
    private String authToken;
    @Value("${twilio.account.sid}")
    private String accountSid;
    @Value("${twilio.account.verificationServiceSID}")
    private String verificationServiceSID;
    @Value("${twilio.account.phoneNumber}")
    private String phoneNumber;
    @Value("${twilio.account.messagingServiceSid}")
    private String messagingServiceSID;


    public TwilioConfig(){
        Twilio.init("AC01981d6d0df613ccfe716d9ccaed85ca", "904781ceab8f697ee24cc3ccf9c787df");
    }


}

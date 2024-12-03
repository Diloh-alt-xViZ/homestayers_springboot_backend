package com.developer.homestayersbackend.util;

import com.developer.homestayersbackend.dto.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class PhoneNumberUtils {
    private static final String defaultCountryCode = "+263";
    private static final String PHONE_NUMBER_REGEX = "^(0\\d{9}|\\+(\\d{1,3})(\\d{9}))$";
    public static PhoneNumber getPhoneNumber(String phoneNumber) {
        PhoneNumber phoneNumberObj = new PhoneNumber();
        phoneNumber = phoneNumber.replaceAll("\\s+", "");
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);

        if(matcher.matches()){
            if(phoneNumber.startsWith("0")){
                PhoneNumber phoneNumber1 = new PhoneNumber();
                phoneNumber1.setNumber(matcher.group(1).substring(1));
                phoneNumber1.setCountryCode("0");
            }

            else {
                String countryCode = matcher.group(2);
                String number = matcher.group(3);
                phoneNumberObj.setCountryCode(countryCode);
                phoneNumberObj.setNumber(number);
            }
        }
        else {
            throw new IllegalArgumentException("Invalid phone number");
        }

        return phoneNumberObj;
    }
    public static String formatToE164(String phoneNumber,String countryCode) {
        System.out.println("Number before formatting: " + phoneNumber);
        phoneNumber = phoneNumber.replaceAll(" ", "");
        if (!phoneNumber.startsWith("+")) {
            phoneNumber = defaultCountryCode + phoneNumber;
            return phoneNumber;
        } else {

            return phoneNumber;
        }


    }
}

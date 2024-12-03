package com.developer.homestayersbackend.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class    UserProfileDetailsDto {

    //@NotBlank(message = "National ID is required")
    //@Pattern(regexp = "^\\d{2}-\\d{6}[A-Z]?\\d{0,2}$", message = "Invalid National ID format")

    private String idNumber;
    private String firstName;
    private String lastName;
    //@NotBlank(message = "Email is required")
    //@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email address")
    private String email;

    //@NotBlank(message = "Phone number is required")
    //@Pattern(regexp = "^\\+\\d{1,3}\\d{9,12}$", message = "Invalid phone number format")
    private String phoneNumber;
    private String password;
    private String gender;
    private Date dob;
}

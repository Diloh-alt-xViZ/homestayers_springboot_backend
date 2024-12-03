package com.developer.homestayersbackend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserProfile {
    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", user=" + user +
                ", intro='" + intro + '\'' +
                ", photo=" + photo +
                ", bio='" + bio + '\'' +
                ", school='" + school + '\'' +
                ", work='" + work + '\'' +
                ", address=" + address +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                '}';
    }

    @Id
    @GeneratedValue
    @Column(name = "user_profile_id")
    private Long id;
    private String firstName;
    private String lastName;
    @OneToOne(cascade = CascadeType.REMOVE)
    private User user;
    private String intro;
    @OneToOne
    private Photo photo;
    private String bio;
    private String school;
    private String work;
    @Embedded
    private Address address;
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String nationalId;
}

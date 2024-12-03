package com.developer.homestayersbackend.entity;

import com.developer.homestayersbackend.util.ReviewStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    private String comment;
    private Date createdAt;
    private int rating;
    @ManyToOne(fetch = FetchType.LAZY)
    private Property property;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;
}

package com.developer.homestayersbackend.entity;


import com.developer.homestayersbackend.util.ReviewStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReviewReply {

    @Id
    @GeneratedValue
    private Long id;
    private String comment;
    private Date createdAt;
    @OneToOne
    private Review replyReview;
    @OneToOne
    private Review review;
    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

}

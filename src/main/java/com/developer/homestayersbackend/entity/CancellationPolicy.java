package com.developer.homestayersbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class CancellationPolicy {
    @Id
    @GeneratedValue
    @Column(name = "cancellation_policy_id")
    private Long id;
    private String name;
    private String description;
    private  int duration;
}

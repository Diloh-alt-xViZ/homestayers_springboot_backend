package com.developer.homestayersbackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomAttachment {
    @Id
    @GeneratedValue
    private int id;
    @Enumerated(EnumType.STRING)
    private AttachmentType attachmentType;
    private String description;
}

package com.developer.homestayersbackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {

    public void setBookedDates(List<Date> bookedDates) {
        System.out.println("Our booked dates: " + bookedDates);
        this.bookedDates.addAll(bookedDates);
    }


    @Id
    @GeneratedValue
    private Long id;
    private String roomTitle;

    @ElementCollection
    @CollectionTable(
            name = "room_booked_dates",
            joinColumns = {
                    @JoinColumn(name = "room_id")
            }
    )
    @Column(name = "booked_date")
    private List<Date> bookedDates;
    private String description;
    @OneToMany
    private List<Photo> photos;
    @ManyToMany
    private List<Amenity> amenities;
    @ManyToMany
    private List<Services> services;
    @OneToMany
    private List<Review> reviews;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    @OneToOne
    private Price price;
    @ManyToMany
    private List<RoomAttachment> roomAttachments;

}

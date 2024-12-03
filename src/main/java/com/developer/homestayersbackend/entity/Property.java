package com.developer.homestayersbackend.entity;


import com.developer.homestayersbackend.util.ApprovalStatus;
import com.developer.homestayersbackend.util.ListingType;
import com.developer.homestayersbackend.util.ServiceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "property")
public class Property {

    public void setBookedDates(List<Date> bookedDates) {
        System.out.println("Our booked dates: " + bookedDates);
        this.bookedDates.addAll(bookedDates);
    }

    @Id
    @GeneratedValue
    @Column(name = "property_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Host host;
    @OneToOne()
    private Location location;
    private String neighbourhoodDetails;
    private String gettingAroundDetails;
    @Enumerated(EnumType.STRING)
    private ListingType listingType;
    private String title;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    @Enumerated(EnumType.STRING)
    private ServiceStatus status;
    @OneToMany
    private List<Room> rooms;
    @ElementCollection
    @CollectionTable(name = "property_booked_dates", joinColumns = {
            @JoinColumn(name = "property_id")

    })
    @Column(name = "booked_date")
    private List<Date> bookedDates;
    @OneToMany
    @JoinTable(
            name = "property_photos_join",
            joinColumns = {
                    @JoinColumn(name = "property_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "photo_id")
            }
    )
    private List<Photo> photos;
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;
    @ManyToMany()
    @JoinTable(name = "property_amenities_join",joinColumns = {
            @JoinColumn(name = "property_id")
    },inverseJoinColumns = {
            @JoinColumn(name = "amenity_id")
    })
    private List<Amenity> amenities;
    @ManyToMany()
    @JoinTable(name = "property_service_join",joinColumns = {
            @JoinColumn(name = "property_id")
    },inverseJoinColumns = {
            @JoinColumn(name = "service_id")
    })
    private List<Services> services;

    @ManyToMany
    @JoinTable(
            name = "property_house_rules_join"
            ,joinColumns = {
                    @JoinColumn(name = "property_id")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "house_rule_id")
            }
    )
    private List<HouseRule> houseRules;
    @OneToMany(mappedBy = "propertyId")
    private List<CustomHouseRule> customHouseRules;
    @OneToOne
    private Price price;
    @OneToMany(mappedBy = "property")
    private List<Review> reviews;

}

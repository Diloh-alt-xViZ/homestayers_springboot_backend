package com.developer.homestayersbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Booking {

    private Date calculateMonthlyEndDate(Date startDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return new Date(calendar.getTime().getTime());
    }

    public void setBookedDates() {
        this.bookedDates = generateBookedDates(startDate, endDate);
    }
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private User guest;
    private Long numberOfGuests;
    @OneToOne
    Room room;
    @OneToOne(fetch = FetchType.LAZY)
    private Property property;
    private Date dateBooked;
    private Date startDate;
    private Date endDate;
    @OneToOne(fetch = FetchType.LAZY)
    private Host host;
    @ElementCollection
    @CollectionTable(name = "booked_dates", joinColumns = {
            @JoinColumn(name = "booking_id")

    })
    @Column(name = "booked_date")
    private List<Date> bookedDates;
    private Date dateUpdated;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private BigDecimal price;


    private List<Date> generateBookedDates(Date startDate, Date endDate) {
        List<Date> bookedDates = new ArrayList<>();
        Date newEndDate = null;
        Date currentDate = new Date(startDate.getTime());


        if(endDate==null) {
            newEndDate = calculateMonthlyEndDate(startDate);
            bookedDates = (startDate.toLocalDate().datesUntil(newEndDate.toLocalDate().plusDays(1)).map(
                    Date::valueOf
            ).toList());
        }

        else {
           bookedDates = startDate.toLocalDate().datesUntil(endDate.toLocalDate().plusDays(1)).map(Date::valueOf).toList();
        }


        return bookedDates;
    }
}

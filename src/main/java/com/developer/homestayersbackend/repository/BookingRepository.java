package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.Booking;
import com.developer.homestayersbackend.entity.BookingStatus;
import com.developer.homestayersbackend.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    Booking findBookingByRoomIdAndBookedDatesContaining(Long roomId, Date bookedDate);
    List<Booking> findBookingByHostId(Long hostId);
    List<Booking> findBookingByGuestId(Long guestId);
    List<Booking> findBookingsByBookingStatus(BookingStatus bookingStatus);
    List<Booking> findByPropertyAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Property property, Date endDate, Date startDate);
}

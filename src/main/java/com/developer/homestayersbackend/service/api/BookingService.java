package com.developer.homestayersbackend.service.api;

import com.developer.homestayersbackend.dto.*;
import com.developer.homestayersbackend.entity.Booking;

import java.util.List;

public interface BookingService {

    Booking createBooking(BookingRequest booking) throws Exception;

    List<Booking> getBookingsByStatus(String bookingStatus);

    List<Booking> getBookingsByUser(Long userId);
    List<BookingStatusDto> getBookingStatuses();
    boolean denyBookingRequest(BookingDenialRequest request);
    List<ReservationDto> getHostBookings(Long hostId);

    boolean approveBooking(Long bookingId);

    BookingResponseDto bookRoom(BookingRequest request);

    BookingResponseDto bookRental(RentalBookingRequest request);

    String acceptBooking(Long bookingId);

    String rejectBooking(Long bookingId);
}

package com.developer.homestayersbackend.controller;

import com.developer.homestayersbackend.dto.*;
import com.developer.homestayersbackend.entity.Booking;
import com.developer.homestayersbackend.entity.BookingStatus;
import com.developer.homestayersbackend.service.api.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private final BookingService bookingService;


    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest booking) throws Exception{

        System.out.println("Booking Request:"+ booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }


    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/room/book")
    public ResponseEntity<BookingResponseDto> bookRoom(@RequestBody BookingRequest request){
        System.out.println("Request:"+request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.bookRoom(request));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/rental/book")
    public ResponseEntity<BookingResponseDto> bookRental(@RequestBody RentalBookingRequest request){


        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.bookRental(request));
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping()
    public ResponseEntity<List<Booking>> getBookingsByStatus(
            @RequestParam("bookingStatus")String bookingStatus
    ) throws Exception{

        List<Booking> filteredBookings = bookingService.getBookingsByStatus(bookingStatus);
        if(filteredBookings.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(bookingService.getBookingsByStatus(bookingStatus));

    }


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/guests/{userId}")
    public ResponseEntity<Booking> getGuestBookings(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(null);
    }


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{bookingId}/accept")
    public ResponseEntity<String> acceptBooking(@PathVariable("bookingId") Long bookingId) {
        return ResponseEntity.ok(bookingService.acceptBooking(bookingId));
    }


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{bookingId}/reject")
    public ResponseEntity<String> rejectBooking(@PathVariable("bookingId") Long bookingId) {

        return ResponseEntity.ok(bookingService.rejectBooking(bookingId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/hosts/{hostId}")
    public ResponseEntity<List<ReservationDto>> getHostReservations(@PathVariable("hostId")Long hostId){

        return ResponseEntity.ok(bookingService.getHostBookings(hostId));
    }


    @GetMapping("/booking-status")
    public ResponseEntity<List<BookingStatusDto>> getBookingStatuses(){
        System.out.println("Booking Status");
        List<BookingStatusDto> bookingStatuses = bookingService.getBookingStatuses();
        System.out.println("Booking Statuses:"+bookingStatuses);
        return ResponseEntity.ok(bookingStatuses);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("{bookingId}/approve-booking")
    public ResponseEntity<String> approveBooking(@PathVariable Long bookingId) throws Exception{

        if(bookingService.approveBooking(bookingId)){
            return ResponseEntity.status(HttpStatus.OK).body("Booking approved successfully");
        }
        else {
            return ResponseEntity.ok("Failed to approve");
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/deny-booking")
    public ResponseEntity<String> denyBooking(@RequestBody BookingDenialRequest request) throws Exception{

        if(bookingService.denyBookingRequest(request)){
            return ResponseEntity.ok("Success");
        }

        else {
            return ResponseEntity.ok("Failure");
        }


    }


}

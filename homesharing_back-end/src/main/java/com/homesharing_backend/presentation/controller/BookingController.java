package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.presentation.payload.request.BookingRequest;
import com.homesharing_backend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create-booking")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest bookingRequest,
                                           @RequestParam("post-id") Long postID) {
        return bookingService.booking(bookingRequest, postID);
    }

    @PutMapping("/edit-booking")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> editBooking(@RequestBody BookingRequest bookingRequest,
                                         @RequestParam("booking-id") Long bookingID) {
        return bookingService.editBooking(bookingRequest, bookingID);
    }

    @PutMapping("/cancel-booking")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> cancelBooking(@RequestParam("booking-id") Long bookingID) {
        return bookingService.cancelBooking(bookingID);
    }

    @GetMapping("/history-booking-customer")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> historyBookingByCustomerID() {
        return bookingService.historyBookingByCustomerID();
    }

    @GetMapping("/get-one-bookingID")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> getOneBookingOfCustomerByBookingID(@RequestParam("booking-id") Long bookingID) {
        return bookingService.getOneBookingOfCustomerByBookingID(bookingID);
    }

    @GetMapping("/all-booking-host")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> getAllBookingByHostID() {
        return bookingService.getAllBookingByHostID();
    }

    @PutMapping("/confirm-booking")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> confirmBooking(@RequestParam("booking-id") Long bookingID) {
        return bookingService.confirmBooking(bookingID);
    }

    @GetMapping("/all-booking-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllBookingByAdmin() {
        return bookingService.getAllBookingByAdmin();
    }

    @PutMapping("/update-status-booking")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> updateStatusBookingByBookingID(@RequestParam("booking-id") Long bookingID) {
        return bookingService.updateBooking(bookingID);
    }

    @PutMapping("/checkout-all-booking")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> checkoutAllBooking() {
        return bookingService.checkoutBookingEndDate();
    }

}

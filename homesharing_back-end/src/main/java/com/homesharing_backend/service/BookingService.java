package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.BookingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {

    public ResponseEntity<MessageResponse> booking(BookingRequest bookingRequest, Long postID);

    public ResponseEntity<JwtResponse> checkVoucher(String code, Long postID);

    public ResponseEntity<MessageResponse> editBooking(BookingRequest bookingRequest, Long bookingID);

    public ResponseEntity<MessageResponse> updateBooking(Long bookingID);

    public ResponseEntity<MessageResponse> checkoutBookingEndDate();

    public ResponseEntity<MessageResponse> cancelBooking(Long bookingID);

    public ResponseEntity<MessageResponse> confirmBooking(Long bookingID, int type);

    public ResponseEntity<JwtResponse> getOneBookingOfCustomerByBookingID(Long bookingID);

    public ResponseEntity<ResponseObject> historyBookingByCustomerID(int indexPage);

    public ResponseEntity<JwtResponse> getAllBookingByHostID();

    public ResponseEntity<JwtResponse> getAllBookingByAdmin();

    public ResponseEntity<MessageResponse> checkInHomestay(Long bookingID, int status);
}

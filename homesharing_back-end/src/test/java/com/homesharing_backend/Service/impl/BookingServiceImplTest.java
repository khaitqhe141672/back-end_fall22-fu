package com.homesharing_backend.Service.impl;

import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.request.BookingRequest;
import com.homesharing_backend.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;

class BookingServiceImplTest {
    @Mock
    BookingDetailRepository bookingDetailRepository;
    @Mock
    BookingRepository bookingRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    PostRepository postRepository;
    @Mock
    HostRepository hostRepository;
    @Mock
    PostUtilityRepository postUtilityRepository;
    @Mock
    BookingUtilityRepository bookingUtilityRepository;
    @Mock
    AdminRepository adminRepository;
    @Mock
    PostImageRepository postImageRepository;
    @Mock
    PostServiceRepository postServiceRepository;
    @Mock
    BookingServiceRepository bookingServiceRepository;
    @Mock
    PostVoucherRepository postVoucherRepository;
    @Mock
    RateRepository rateRepository;
    @InjectMocks
    BookingServiceImpl bookingServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBooking() {
        when(customerRepository.getCustomerByUser_Id(anyLong())).thenReturn(new Customer(Long.valueOf(1), null));
        when(postRepository.getPostById(anyLong())).thenReturn(new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0));
        when(postServiceRepository.getPostServicesByIdAndPost_Id(anyLong(), anyLong())).thenReturn(new PostServices(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), null, 0f, 0));
        when(postVoucherRepository.getPostVoucherByIdAndPost_Id(anyLong(), anyLong())).thenReturn(new PostVoucher(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), null, null, null, 0));

        ResponseEntity<MessageResponse> result = bookingServiceImpl.booking(new BookingRequest(null, null, "note", 0f, 0, List.of(Long.valueOf(1)), Long.valueOf(1), 0f, 0f, 0f, "fullName", "mobile", "email"), Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCheckVoucher() {
        when(postVoucherRepository.getPostVoucherByPost_IdAndVoucher_Code(anyLong(), anyString())).thenReturn(new PostVoucher(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), null, null, null, 0));

        ResponseEntity<JwtResponse> result = bookingServiceImpl.checkVoucher("code", Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testEditBooking() {
        when(bookingDetailRepository.getBookingDetailByBooking_Id(anyLong())).thenReturn(new BookingDetail(Long.valueOf(1), new Booking(Long.valueOf(1), new Customer(Long.valueOf(1), null), 0f, "note", null, 0), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new PostVoucher(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), null, null, null, 0), 0f, 0f, 0f, null, null, 0, "fullName", "mobile", "email"));
        when(bookingRepository.getBookingById(anyLong())).thenReturn(new Booking(Long.valueOf(1), new Customer(Long.valueOf(1), null), 0f, "note", null, 0));

        ResponseEntity<MessageResponse> result = bookingServiceImpl.editBooking(new BookingRequest(null, null, "note", 0f, 0, List.of(Long.valueOf(1)), Long.valueOf(1), 0f, 0f, 0f, "fullName", "mobile", "email"), Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testUpdateBooking() {
        when(bookingRepository.getBookingById(anyLong())).thenReturn(new Booking(Long.valueOf(1), new Customer(Long.valueOf(1), null), 0f, "note", null, 0));

        ResponseEntity<MessageResponse> result = bookingServiceImpl.updateBooking(Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCheckoutBookingEndDate() {
        when(bookingDetailRepository.getAllBookingDetailByEndDate(any())).thenReturn(List.of(new BookingDetail(Long.valueOf(1), new Booking(Long.valueOf(1), new Customer(Long.valueOf(1), null), 0f, "note", null, 0), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new PostVoucher(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), null, null, null, 0), 0f, 0f, 0f, null, null, 0, "fullName", "mobile", "email")));
        when(bookingRepository.getBookingById(anyLong())).thenReturn(new Booking(Long.valueOf(1), new Customer(Long.valueOf(1), null), 0f, "note", null, 0));
        when(hostRepository.getHostsByUser_Id(anyLong())).thenReturn(new Host(Long.valueOf(1), new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0), 0));

        ResponseEntity<MessageResponse> result = bookingServiceImpl.checkoutBookingEndDate();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCancelBooking() {
        when(bookingDetailRepository.getBookingDetailByBooking_Id(anyLong())).thenReturn(new BookingDetail(Long.valueOf(1), new Booking(Long.valueOf(1), new Customer(Long.valueOf(1), null), 0f, "note", null, 0), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new PostVoucher(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), null, null, null, 0), 0f, 0f, 0f, null, null, 0, "fullName", "mobile", "email"));
        when(bookingRepository.getBookingById(anyLong())).thenReturn(new Booking(Long.valueOf(1), new Customer(Long.valueOf(1), null), 0f, "note", null, 0));

        ResponseEntity<MessageResponse> result = bookingServiceImpl.cancelBooking(Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testConfirmBooking() {
        when(bookingRepository.getBookingById(anyLong())).thenReturn(new Booking(Long.valueOf(1), new Customer(Long.valueOf(1), null), 0f, "note", null, 0));

        ResponseEntity<MessageResponse> result = bookingServiceImpl.confirmBooking(Long.valueOf(1), 0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetOneBookingOfCustomerByBookingID() {
        ResponseEntity<JwtResponse> result = bookingServiceImpl.getOneBookingOfCustomerByBookingID(Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testHistoryBookingByCustomerID() {
        when(bookingDetailRepository.getBookingDetailByBooking_Id(anyLong())).thenReturn(new BookingDetail(Long.valueOf(1), new Booking(Long.valueOf(1), new Customer(Long.valueOf(1), null), 0f, "note", null, 0), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new PostVoucher(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), null, null, null, 0), 0f, 0f, 0f, null, null, 0, "fullName", "mobile", "email"));
        when(bookingRepository.getBookingByCustomer_Id(anyLong())).thenReturn(List.of(new Booking(Long.valueOf(1), new Customer(Long.valueOf(1), null), 0f, "note", null, 0)));
        when(customerRepository.getCustomerByUser_Id(anyLong())).thenReturn(new Customer(Long.valueOf(1), null));
        when(postImageRepository.findPostImageByPost_Id(anyLong())).thenReturn(List.of(new PostImage(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), "imageUrl")));
        when(rateRepository.getRateByBookingDetail_Id(anyLong())).thenReturn(new Rate(Long.valueOf(1), new BookingDetail(Long.valueOf(1), new Booking(Long.valueOf(1), new Customer(Long.valueOf(1), null), 0f, "note", null, 0), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new PostVoucher(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), null, null, null, 0), 0f, 0f, 0f, null, null, 0, "fullName", "mobile", "email"), 0, "comment", null, 0));

        ResponseEntity<JwtResponse> result = bookingServiceImpl.historyBookingByCustomerID();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAllBookingByHostID() {
        when(bookingDetailRepository.getBookingDetailByPost_Host_Id(anyLong())).thenReturn(List.of(new BookingDetail(Long.valueOf(1), new Booking(Long.valueOf(1), new Customer(Long.valueOf(1), null), 0f, "note", null, 0), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new PostVoucher(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), null, null, null, 0), 0f, 0f, 0f, null, null, 0, "fullName", "mobile", "email")));
        when(hostRepository.getHostsByUser_Id(anyLong())).thenReturn(new Host(Long.valueOf(1), new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0), 0));
        when(postImageRepository.findPostImageByPost_Id(anyLong())).thenReturn(List.of(new PostImage(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), "imageUrl")));

        ResponseEntity<JwtResponse> result = bookingServiceImpl.getAllBookingByHostID();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAllBookingByAdmin() {
        when(bookingDetailRepository.getBookingDetailByBooking_Id(anyLong())).thenReturn(new BookingDetail(Long.valueOf(1), new Booking(Long.valueOf(1), new Customer(Long.valueOf(1), null), 0f, "note", null, 0), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new PostVoucher(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), null, null, null, 0), 0f, 0f, 0f, null, null, 0, "fullName", "mobile", "email"));
        when(postImageRepository.findPostImageByPost_Id(anyLong())).thenReturn(List.of(new PostImage(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), "imageUrl")));

        ResponseEntity<JwtResponse> result = bookingServiceImpl.getAllBookingByAdmin();
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
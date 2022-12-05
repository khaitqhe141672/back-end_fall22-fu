package com.homesharing_backend.Service.impl;

import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.request.RateRequest;
import com.homesharing_backend.service.impl.RateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;

class RateServiceImplTest {
    @Mock
    RateRepository rateRepository;
    @Mock
    PostRepository postRepository;
    @Mock
    BookingDetailRepository bookingDetailRepository;
    @Mock
    BookingRepository bookingRepository;
    @Mock
    LikesDislikesRepository likesDislikesRepository;
    @InjectMocks
    RateServiceImpl rateServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRate() {
        when(rateRepository.findAllByBookingDetail_Post_Id(anyLong())).thenReturn(List.of(new Rate(Long.valueOf(1), null, 0, "comment", null, 0)));
        when(postRepository.existsPostById(anyLong())).thenReturn(Boolean.TRUE);
        when(likesDislikesRepository.countLikesDislikesByRate_IdAndType(anyLong(), anyInt())).thenReturn(0);

        ResponseEntity<JwtResponse> result = rateServiceImpl.getAllRate(Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCreateRateByCustomer() {
        when(bookingDetailRepository.getBookingDetailById(anyLong())).thenReturn(new BookingDetail(Long.valueOf(1), new Booking(Long.valueOf(1), new Customer(Long.valueOf(1), null), 0f, "note", null, 0), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new PostVoucher(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new Voucher(Long.valueOf(1), null, "code", "description", 0, 0, 0), null, null, 0), 0f, 0f, 0f, null, null, 0, "fullName", "mobile", "email"));

        ResponseEntity<MessageResponse> result = rateServiceImpl.createRateByCustomer(new RateRequest("comment", 0), Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testEditRateByCustomer() {
        when(rateRepository.getRateById(anyLong())).thenReturn(new Rate(Long.valueOf(1), null, 0, "comment", null, 0));

        ResponseEntity<MessageResponse> result = rateServiceImpl.editRateByCustomer(new RateRequest("comment", 0), Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testDeleteRateByCustomer() {
        when(rateRepository.getRateById(anyLong())).thenReturn(new Rate(Long.valueOf(1), null, 0, "comment", null, 0));

        ResponseEntity<MessageResponse> result = rateServiceImpl.deleteRateByCustomer(Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
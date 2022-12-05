package com.homesharing_backend.Service.impl;

import com.homesharing_backend.data.entity.PaymentPackage;
import com.homesharing_backend.data.entity.Post;
import com.homesharing_backend.data.entity.PostPayment;
import com.homesharing_backend.data.repository.PaymentPackageRepository;
import com.homesharing_backend.data.repository.PostPaymentRepository;
import com.homesharing_backend.data.repository.PostRepository;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.PaymentRequest;
import com.homesharing_backend.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

class PaymentServiceImplTest {
    @Mock
    PaymentPackageRepository paymentPackageRepository;
    @Mock
    PostPaymentRepository postPaymentRepository;
    @Mock
    PostRepository postRepository;
    @InjectMocks
    PaymentServiceImpl paymentServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRePayment() {
        ResponseEntity<ResponseObject> result = paymentServiceImpl.rePayment(Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCreatePayment() {
        when(paymentPackageRepository.getPaymentPackageById(anyLong())).thenReturn(new PaymentPackage(Long.valueOf(1), "name", 0, 0, 0));

        ResponseEntity<ResponseObject> result = paymentServiceImpl.createPayment(new PaymentRequest(Long.valueOf(1), Long.valueOf(1)));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testPaymentResult() {
        when(paymentPackageRepository.getPaymentPackageById(anyLong())).thenReturn(new PaymentPackage(Long.valueOf(1), "name", 0, 0, 0));
        when(postPaymentRepository.getPostPaymentByPost_IdAndStatusAndPaymentPackage_Id(anyLong(), anyInt(), anyLong())).thenReturn(new PostPayment(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new PaymentPackage(Long.valueOf(1), "name", 0, 0, 0), null, null, 0));
        when(postRepository.getPostById(anyLong())).thenReturn(new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0));

        ResponseEntity<JwtResponse> result = paymentServiceImpl.paymentResult(Long.valueOf(1), Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.entity.PaymentPackage;
import com.homesharing_backend.data.repository.PaymentPackageRepository;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.PaymentPackageRequest;
import com.homesharing_backend.service.PaymentPackageService;
import com.homesharing_backend.service.PaymentService;
import com.homesharing_backend.service.PostVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PaymentPackageServiceImpl implements PaymentPackageService {

    @Autowired
    private PaymentPackageRepository paymentPackageRepository;

    @Autowired
    private PostVoucherService postVoucherService;

    @Autowired
    private PaymentService paymentService;

    @Override
    public ResponseEntity<ResponseObject> getAllPaymentPackage() {

        paymentService.checkTimePostPayment();
        postVoucherService.checkTimePostVoucher();

        List<PaymentPackage> paymentPackages = paymentPackageRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("List payment package", new HashMap<>() {
            {
                put("payment-package", paymentPackages);
            }
        }));
    }

    @Override
    public ResponseEntity<ResponseObject> insertPaymentPackage(PaymentPackageRequest paymentPackageRequest) {

        paymentService.checkTimePostPayment();
        postVoucherService.checkTimePostVoucher();

        PaymentPackage paymentPackage = PaymentPackage.builder()
                .name(paymentPackageRequest.getName())
                .price(paymentPackageRequest.getPrice())
                .dueMonth(paymentPackageRequest.getDueMonth())
                .status(1)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Save success payment package", new HashMap<>() {
            {
                put("payment-package", paymentPackageRepository.save(paymentPackage));
            }
        }));
    }

    @Override
    public ResponseEntity<ResponseObject> updatePaymentPackage(Long id, PaymentPackageRequest paymentPackageRequest) {

        paymentService.checkTimePostPayment();
        postVoucherService.checkTimePostVoucher();

        PaymentPackage paymentPackage = paymentPackageRepository.getPaymentPackageById(id);

        paymentPackage.setDueMonth(paymentPackageRequest.getDueMonth());
        paymentPackage.setName(paymentPackageRequest.getName());
        paymentPackage.setPrice(paymentPackageRequest.getPrice());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Update success payment package", new HashMap<>() {
            {
                put("payment-package", paymentPackageRepository.save(paymentPackage));
            }
        }));
    }

    @Override
    public ResponseEntity<ResponseObject> getOnePaymentPackage(Long id) {

        paymentService.checkTimePostPayment();
        postVoucherService.checkTimePostVoucher();

        PaymentPackage paymentPackage = paymentPackageRepository.getPaymentPackageById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Get one payment package by ID", new HashMap<>() {
            {
                put("payment-package", paymentPackage);
            }
        }));
    }

    /*
        status = 1 show
        status = 2 pause
        status = 3 delete
     */
    @Override
    public ResponseEntity<ResponseObject> updateStatus(Long id, int status) {

        PaymentPackage paymentPackage = paymentPackageRepository.getPaymentPackageById(id);

        if(status == 1){
            paymentPackage.setStatus(1);
        } else  if(status == 2){
            paymentPackage.setStatus(2);
        } else {
            paymentPackage.setStatus(3);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Update status payment package by ID", new HashMap<>() {
            {
                put("payment-package", paymentPackageRepository.save(paymentPackage));
            }
        }));
    }
}

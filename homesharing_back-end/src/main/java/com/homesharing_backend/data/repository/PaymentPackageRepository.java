package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.PaymentPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentPackageRepository extends JpaRepository<PaymentPackage, Long> {

    PaymentPackage getPaymentPackageById(Long id);

}

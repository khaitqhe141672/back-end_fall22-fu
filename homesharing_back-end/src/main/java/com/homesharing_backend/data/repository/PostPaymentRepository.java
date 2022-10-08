package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.PostPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPaymentRepository extends JpaRepository<PostPayment, Long> {
}

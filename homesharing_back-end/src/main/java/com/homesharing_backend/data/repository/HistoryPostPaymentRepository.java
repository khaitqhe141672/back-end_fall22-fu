package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.HistoryPostPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryPostPaymentRepository extends JpaRepository<HistoryPostPayment, Long> {

    Page<HistoryPostPayment> getHistoryPostPaymentByPost_Id(Long postID, PageRequest pageRequest);
}

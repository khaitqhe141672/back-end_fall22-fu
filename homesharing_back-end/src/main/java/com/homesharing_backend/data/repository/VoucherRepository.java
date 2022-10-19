package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    Voucher getVoucherById(Long id);

    Optional<Voucher> findVoucherById(Long id);

}

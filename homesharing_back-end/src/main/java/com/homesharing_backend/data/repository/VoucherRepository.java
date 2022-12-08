package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    Voucher getVoucherById(Long id);

    Optional<Voucher> findVoucherById(Long id);

    List<Voucher> getVoucherByHost_IdAndStatus(Long hostID, int status);

    Page<Voucher> getVoucherByHost_Id(Long hostID, PageRequest pageRequest);

    Voucher getVoucherByIdAndHost_Id(Long voucherID, Long hostID);

    List<Voucher> getVoucherByStatus(int status);
}

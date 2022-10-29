package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.VoucherDto;
import com.homesharing_backend.data.entity.Host;
import com.homesharing_backend.data.entity.User;
import com.homesharing_backend.data.entity.Voucher;
import com.homesharing_backend.data.repository.HostRepository;
import com.homesharing_backend.data.repository.VoucherRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.exception.SaveDataException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.VoucherRequest;
import com.homesharing_backend.service.VoucherService;
import com.homesharing_backend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private HostRepository hostRepository;

    @Override
    public ResponseEntity<ResponseObject> getAllVoucher() {

        List<Voucher> vouchers = voucherRepository.findAll();
        List<VoucherDto> dtoList = new ArrayList<>();

        for (Voucher v : vouchers) {
            VoucherDto dto = VoucherDto.builder()
                    .nameVoucher(v.getNameVoucher())
                    .description(v.getDescription())
                    .dueDate(v.getDueDay())
                    .percent(v.getPercent())
                    .status(v.getStatus())
                    .build();
            dtoList.add(dto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("List voucher", new HashMap<>() {
            {
                put("vouchers", dtoList);
            }
        }));
    }

    @Override
    public ResponseEntity<ResponseObject> getOneVoucher(Long id) {

        Voucher voucher = voucherRepository.getVoucherById(id);

        VoucherDto dto = VoucherDto.builder()
                .nameVoucher(voucher.getNameVoucher())
                .description(voucher.getDescription())
                .dueDate(voucher.getDueDay())
                .percent(voucher.getPercent())
                .status(voucher.getStatus())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Get onr voucher by ID", new HashMap<>() {
            {
                put("voucher", dto);
            }
        }));
    }

    @Override
    public ResponseEntity<MessageResponse> insertVoucher(List<VoucherRequest> voucherRequest) {

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

        List<Voucher> vouchers = new ArrayList<>();

        voucherRequest.forEach(vp -> {
            Voucher v = Voucher.builder()
                    .nameVoucher(vp.getName())
                    .description(vp.getDescription())
                    .dueDay(vp.getDueDate())
                    .host(host)
                    .status(1)
                    .build();
            Voucher voucher = voucherRepository.save(v);

            vouchers.add(voucher);

        });

        if (Objects.isNull(vouchers)) {
            throw new SaveDataException("create voucher khong thanh cong");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "Create voucher thanh cong"));
        }

    }

    @Override
    public ResponseEntity<ResponseObject> updateVoucher(Long id, VoucherRequest voucherRequest) {

        Voucher voucher = voucherRepository.getVoucherById(id);

        Voucher v = Voucher.builder()
                .nameVoucher(voucherRequest.getName())
                .description(voucherRequest.getDescription())
                .dueDay(voucherRequest.getDueDate())
                .host(voucher.getHost())
                .status(voucher.getStatus())
                .build();

        Voucher vUpdate = voucherRepository.save(v);

        VoucherDto dto = VoucherDto.builder()
                .nameVoucher(vUpdate.getNameVoucher())
                .description(vUpdate.getDescription())
                .dueDate(vUpdate.getDueDay())
                .percent(vUpdate.getPercent())
                .status(vUpdate.getStatus())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Update success voucher", new HashMap<>() {
            {
                put("voucher", dto);
            }
        }));
    }

    @Override
    public ResponseEntity<ResponseObject> updateStatusVoucher(Long id, int status) {

        Voucher voucher = voucherRepository.getVoucherById(id);

        return null;
    }

    @Override
    public ResponseEntity<JwtResponse> getAllVoucherByHostID() {

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

        List<Voucher> vouchers = voucherRepository.getVoucherByHost_Id(host.getId());

        if (Objects.isNull(vouchers)) {
            throw new NotFoundException("Khong co data voucher cua host_id nay");
        } else {

            List<VoucherDto> dtoList = new ArrayList<>();

            vouchers.forEach(v -> {
                VoucherDto dto = VoucherDto.builder()
                        .nameVoucher(v.getNameVoucher())
                        .description(v.getDescription())
                        .dueDate(v.getDueDay())
                        .percent(v.getPercent())
                        .status(v.getStatus())
                        .build();

                dtoList.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), dtoList));
        }

    }
}

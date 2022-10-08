package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.VoucherDto;
import com.homesharing_backend.data.entity.Host;
import com.homesharing_backend.data.entity.User;
import com.homesharing_backend.data.entity.Voucher;
import com.homesharing_backend.data.repository.HostRepository;
import com.homesharing_backend.data.repository.VoucherRepository;
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

        for (Voucher v : vouchers){
            VoucherDto dto = VoucherDto.builder()
                    .nameVoucher(v.getNameVoucher())
                    .description(v.getDescription())
                    .dueDate(v.getDueDay())
                    .percent(v.getPercent())
                    .hostID(v.getHost().getId())
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
                .hostID(voucher.getHost().getId())
                .status(voucher.getStatus())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Get onr voucher by ID", new HashMap<>() {
            {
                put("voucher", dto);
            }
        }));
    }

    @Override
    public ResponseEntity<ResponseObject> insertVoucher(VoucherRequest voucherRequest) {

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

        Voucher v = Voucher.builder()
                .nameVoucher(voucherRequest.getName())
                .description(voucherRequest.getDescription())
                .dueDay(voucherRequest.getDueDate())
                .host(host)
                .build();

        Voucher voucher = voucherRepository.save(v);

        VoucherDto dto = VoucherDto.builder()
                .nameVoucher(voucher.getNameVoucher())
                .description(voucher.getDescription())
                .dueDate(voucher.getDueDay())
                .percent(voucher.getPercent())
                .hostID(voucher.getHost().getId())
                .status(voucher.getStatus())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Save success voucher", new HashMap<>() {
            {
                put("voucher", dto);
            }
        }));
    }

    @Override
    public ResponseEntity<ResponseObject> updateVoucher(Long id, VoucherRequest voucherRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> updateStatusVoucher(Long id, int status) {
        return null;
    }
}

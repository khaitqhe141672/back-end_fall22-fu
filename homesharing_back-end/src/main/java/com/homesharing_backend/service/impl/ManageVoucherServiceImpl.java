package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.VoucherDto;
import com.homesharing_backend.data.entity.Host;
import com.homesharing_backend.data.entity.Voucher;
import com.homesharing_backend.data.repository.HostRepository;
import com.homesharing_backend.data.repository.VoucherRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.ManageVoucherService;
import com.homesharing_backend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class ManageVoucherServiceImpl implements ManageVoucherService {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public ResponseEntity<ResponseObject> getAllVoucherByHost(int indexPage) {

        int size = 10;
        int page = indexPage - 1;

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());
        Page<Voucher> vouchers = voucherRepository.getVoucherByHost_Id(host.getId(), PageRequest.of(page, size));

        if (Objects.isNull(vouchers)) {
            throw new NotFoundException("khong co voucher theo host_id");
        } else {

            List<VoucherDto> dtoList = new ArrayList<>();

            vouchers.forEach(v -> {
                VoucherDto dto = VoucherDto.builder()
                        .idVoucher(v.getId())
                        .nameVoucher(v.getCode())
                        .percent(v.getPercent())
                        .status(v.getStatus())
                        .dueDate(v.getDueDay())
                        .description(v.getDescription())
                        .build();
                dtoList.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("listVoucher", dtoList);
                    put("sizePage", vouchers.getTotalPages());
                }
            }));
        }
    }
}

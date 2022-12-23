package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.HistoryPostPaymentDto;
import com.homesharing_backend.data.entity.HistoryPostPayment;
import com.homesharing_backend.data.repository.HistoryPostPaymentRepository;
import com.homesharing_backend.data.repository.HostRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.service.HistoryPostPaymentService;
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
public class HistoryPostPaymentServiceImpl implements HistoryPostPaymentService {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private HistoryPostPaymentRepository historyPostPaymentRepository;

    @Override
    public ResponseEntity<ResponseObject> getAllHistoryPostPayment(int index, Long postID) {

        int size = 10;
        int page = index - 1;

        Page<HistoryPostPayment> historyPostPayments = historyPostPaymentRepository.getHistoryPostPaymentByPost_Id(postID, PageRequest.of(page, size));

        if (Objects.isNull(historyPostPayments)) {
            throw new NotFoundException("Khong co post_id nao trong lich su");
        } else {

            List<HistoryPostPaymentDto> list = new ArrayList<>();

            historyPostPayments.forEach(h -> {
                HistoryPostPaymentDto dto = HistoryPostPaymentDto.builder()
                        .historyPostPaymentID(h.getId())
                        .title(h.getPost().getTitle())
                        .postID(h.getPost().getId())
                        .startDate(h.getStartDate())
                        .endDate(h.getEndDate())
                        .paymentName(h.getPaymentPackage().getName())
                        .pricePayment(h.getPaymentPackage().getPrice())
                        .build();
                list.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("totalAccount", list);
                    put("sizePage", historyPostPayments.getTotalPages());
                }
            }));
        }
    }
}

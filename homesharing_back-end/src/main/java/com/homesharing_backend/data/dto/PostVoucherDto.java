package com.homesharing_backend.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
public class PostVoucherDto {

    private Long postVoucherID;
    private Long voucherID;
    private String description;
    private int dueDay;
    private String code;
    private int percent;
    private Date startDate;
    private Date endDate;
    private int status;

    public PostVoucherDto(Long postVoucherID, Long voucherID,
                          String description, int dueDay, String code,
                          int percent, Date startDate, Date endDate, int status) {
        this.postVoucherID = postVoucherID;
        this.voucherID = voucherID;
        this.description = description;
        this.dueDay = dueDay;
        this.code = code;
        this.percent = percent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public PostVoucherDto(Long postVoucherID, Long voucherID, int status) {
        this.postVoucherID = postVoucherID;
        this.voucherID = voucherID;
        this.status = status;
    }
}

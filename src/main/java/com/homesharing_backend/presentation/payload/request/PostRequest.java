package com.homesharing_backend.presentation.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostRequest {

    private Long roomTypeID;
    private String address;
    private int guestNumber;
    private int numberOfBathrooms;
    private int numberOfBedrooms;
    private int numberOfBeds;
    private String title;
    private String description;
    private float price;
    private List<Long> utilityRequests;
    private List<Long> voucherList;
    private Long paymentPackageID;
    private String latitude;
    private String longitude;
    private List<PostServiceRequest> postServiceRequests;
}

package com.homesharing_backend.service.impl;

import com.homesharing_backend.config.PaymentConfig;
import com.homesharing_backend.data.entity.HistoryPostPayment;
import com.homesharing_backend.data.entity.PaymentPackage;
import com.homesharing_backend.data.entity.Post;
import com.homesharing_backend.data.entity.PostPayment;
import com.homesharing_backend.data.repository.HistoryPostPaymentRepository;
import com.homesharing_backend.data.repository.PaymentPackageRepository;
import com.homesharing_backend.data.repository.PostPaymentRepository;
import com.homesharing_backend.data.repository.PostRepository;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.exception.SaveDataException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.PaymentRequest;
import com.homesharing_backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentPackageRepository paymentPackageRepository;

    @Autowired
    private PostPaymentRepository postPaymentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private HistoryPostPaymentRepository historyPostPaymentRepository;

    @Override
    public ResponseEntity<ResponseObject> rePayment(Long paymentPackageID) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> createPayment(PaymentRequest paymentRequest) throws UnsupportedEncodingException {

        PaymentPackage paymentPackage = paymentPackageRepository.getPaymentPackageById(paymentRequest.getPaymentPackageID());

        if (Objects.isNull(paymentPackage)) {
            throw new NotFoundException("khong tim thay payment-packate-id");
        } else {
            int price = paymentPackage.getPrice() * 100;

            String paymentID = paymentRequest.getPaymentPackageID() + "-"
                    + paymentRequest.getPostID() + "-" + PaymentConfig.getRandomNumber(6);

            Map vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", PaymentConfig.VERSION);
            vnp_Params.put("vnp_Command", PaymentConfig.COMMAND);
            vnp_Params.put("vnp_TmnCode", PaymentConfig.vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(price));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", paymentID);
            vnp_Params.put("vnp_OrderInfo", "HomeSharing - Thanh toan hoa don");
            vnp_Params.put("vnp_OrderType", PaymentConfig.ORDER_TYPE);
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl);
            vnp_Params.put("vnp_IpAddr", "127.0.0.1");
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());

            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.vnp_HashSecret, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;
            System.out.println("URL: " + paymentUrl);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", new HashMap<>() {
                {
                    put("mess", "Create payment success");
                    put("urlPayment", paymentUrl);
                    put("status", 10);
                }
            }));
        }

    }

    @Override
    public ResponseEntity<JwtResponse> paymentResult(Long postID, Long packagePaymentID) {


        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();

        Date dateNow = Date.valueOf(localDate);

        PaymentPackage paymentPackage = paymentPackageRepository.getPaymentPackageById(packagePaymentID);

        Post post = postRepository.getPostById(postID);

        int status = 0;

        if (!Objects.isNull(post)) {

            PostPayment pp = postPaymentRepository.getPostPaymentByPost_IdAndStatusAndPaymentPackage_Id(post.getId(), 1, paymentPackage.getId());

            if (Objects.isNull(pp)) {
                PostPayment postPayment = new PostPayment();
                postPayment.setStatus(1);
                postPayment.setStartDate(dateNow);
                postPayment.setEndDate(Date.valueOf(localDate.plusMonths(paymentPackage.getDueMonth())));
                postPayment.setPaymentPackage(paymentPackage);
                postPayment.setPost(post);
                postPaymentRepository.save(postPayment);

                post.setStatus(1);
                postRepository.save(post);

                HistoryPostPayment historyPostPayment = HistoryPostPayment.builder()
                        .post(post)
                        .paymentPackage(paymentPackage)
                        .endDate(Date.valueOf(localDate.plusMonths(paymentPackage.getDueMonth())))
                        .startDate(dateNow)
                        .build();
                historyPostPaymentRepository.save(historyPostPayment);

                status = 200;
            } else {
                status = 300;
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), status));
    }

    @Override
    public ResponseEntity<MessageResponse> checkTimePostPayment() {

        List<PostPayment> postPayments = postPaymentRepository.findAll();

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();

        Date dateNow = Date.valueOf(localDate);

        postPayments.forEach(p -> {
            if (dateNow.after(p.getEndDate())) {
                p.setStatus(2);

                Post post = postRepository.getPostById(p.getId());
                post.setStatus(4);

                postPaymentRepository.save(p);
                postRepository.save(post);
            }
        });

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(HttpStatus.OK.value(), "Add voucher thanh cong"));

    }
}

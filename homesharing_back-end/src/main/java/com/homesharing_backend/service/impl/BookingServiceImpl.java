package com.homesharing_backend.service.impl;

import com.homesharing_backend.data.dto.BookingDto;
import com.homesharing_backend.data.dto.BookingUtilityDto;
import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.exception.DateException;
import com.homesharing_backend.exception.NotFoundException;
import com.homesharing_backend.exception.SaveDataException;
import com.homesharing_backend.exception.UpdateDataException;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.request.BookingRequest;
import com.homesharing_backend.service.BookingService;
import com.homesharing_backend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.DataFormatException;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private PostUtilityRepository postUtilityRepository;

    @Autowired
    private BookingUtilityRepository bookingUtilityRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PostImageRepository postImageRepository;

    /* status = 1 chờ confirm bởi host */
    @Override
    public ResponseEntity<MessageResponse> booking(BookingRequest bookingRequest, Long postID) {

        Post post = postRepository.getPostById(postID);

        if (Objects.isNull(post)) {
            throw new NotFoundException("post_id khong ton tai");
        } else {
            LocalDateTime localDateTime = LocalDateTime.now();
            LocalDate localDate = localDateTime.toLocalDate();

            Date createDate = Date.valueOf(localDate);

            Customer customer = customerRepository.getCustomerByUser_Id(SecurityUtils.getPrincipal().getId());

            Booking booking = Booking.builder()
                    .createDate(createDate)
                    .note(bookingRequest.getNote())
                    .totalMoney(bookingRequest.getTotalMoney())
                    .customer(customer)
                    .status(1)
                    .build();

            Booking saveBooking = bookingRepository.save(booking);

            if (Objects.isNull(saveBooking)) {
                throw new SaveDataException("save booking not success");
            } else {
                BookingDetail bookingDetail = BookingDetail.builder()
                        .booking(saveBooking)
                        .endDate(bookingRequest.getEndDate())
                        .startDate(bookingRequest.getStartDate())
                        .serviceCharge(bookingRequest.getServiceCharge())
                        .totalPerson(bookingRequest.getTotalPerson())
                        .post(post)
                        .build();
                BookingDetail saveBookingDetail = bookingDetailRepository.save(bookingDetail);

                bookingRequest.getPostUtilityID().forEach(p -> {
                    PostUtility postUtility = postUtilityRepository.getPostUtilityById(p);
                    BookingUtility utility = BookingUtility.builder()
                            .booking(saveBooking)
                            .postUtility(postUtility)
                            .build();
                    bookingUtilityRepository.save(utility);
                });

                if (Objects.isNull(saveBookingDetail)) {
                    throw new SaveDataException("save booking_detail not success");
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "booking success"));
                }
            }
        }
    }

    @Override
    public ResponseEntity<MessageResponse> editBooking(BookingRequest bookingRequest, Long bookingID) {
        /*lam sau chua co man cua customer khong biet can nhung thong tin gi*/
        return null;
    }

    /* status = 4 đã trả phòng chức năng của host*/
    @Override
    public ResponseEntity<MessageResponse> updateBooking(Long bookingID) {

        Booking booking = bookingRepository.getBookingById(bookingID);

        if (Objects.isNull(booking)) {
            throw new NotFoundException("Booking_id khong ton tai");
        } else {
            booking.setStatus(4);

            Booking b = bookingRepository.save(booking);

            if (Objects.isNull(b)) {
                throw new UpdateDataException("Update status by booking-id not success");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "update status success success"));
            }
        }
    }

    /*api nay dùng de checkout toan bo danh sach post booking ngay hom day*/
    @Override
    public ResponseEntity<MessageResponse> checkoutBookingEndDate() {

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();

        Date dateNow = Date.valueOf(localDate);

        List<BookingDetail> bookingDetails = bookingDetailRepository.getAllBookingDetailByEndDate(dateNow);

        if (Objects.isNull(bookingDetails)) {
            throw new NotFoundException("Khong tim thay phong nao tra phong hnay");
        } else {
            bookingDetails.forEach(b -> {
                Booking booking = bookingRepository.getBookingById(b.getId());
                booking.setStatus(4);
                bookingRepository.save(booking);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "checkout booking success"));
        }
    }

    /* status = 5 hủy book chức năng hủy phòng của customer điều kiện là phỉa trước ngày bắtđầu*/
    @Override
    public ResponseEntity<MessageResponse> cancelBooking(Long bookingID) {

        Booking booking = bookingRepository.getBookingById(bookingID);

        if (Objects.isNull(booking)) {
            throw new NotFoundException("Booking_id khong ton tai");
        } else {

            BookingDetail bookingDetail = bookingDetailRepository.getBookingDetailByBooking_Id(booking.getId());

            LocalDateTime localDateTime = LocalDateTime.now();
            LocalDate localDate = localDateTime.toLocalDate();

            Date dateNow = Date.valueOf(localDate);

            if (dateNow.before(bookingDetail.getStartDate())) {
                booking.setStatus(5);
                Booking b = bookingRepository.save(booking);
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "cancel booking success"));
            } else if (dateNow.after(bookingDetail.getStartDate())) {
                throw new DateException("Khong dc huy");
            } else {
                throw new DateException("Khong dc huy");
            }
        }
    }

    /* status = 2 xác nhận thuê thành công */
    @Override
    public ResponseEntity<MessageResponse> confirmBooking(Long bookingID) {

        Booking booking = bookingRepository.getBookingById(bookingID);

        if (Objects.isNull(booking)) {
            throw new NotFoundException("Booking_id khong ton tai");
        } else {
            booking.setStatus(2);

            Booking b = bookingRepository.save(booking);

            if (Objects.isNull(b)) {
                throw new UpdateDataException("Confirm booking not success");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(200, "Confirm booking success"));
            }
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getOneBookingOfCustomerByBookingID(Long bookingID) {
        /*lam sau chua co man cua customer khong biet can nhung thong tin gi*/
        return null;
    }

    @Override
    public ResponseEntity<JwtResponse> historyBookingByCustomerID() {

        Customer customer = customerRepository.getCustomerByUser_Id(SecurityUtils.getPrincipal().getId());

        List<Booking> booking = bookingRepository.getBookingByCustomer_Id(customer.getId());

        if (Objects.isNull(booking)) {
            throw new NotFoundException("Customer khong co booking");
        } else {

            List<BookingDto> bookingDtoList = new ArrayList<>();

            booking.forEach(b -> {

                BookingDetail bookingDetail = bookingDetailRepository.getBookingDetailByBooking_Id(b.getId());
                List<PostImage> postImages = postImageRepository.findPostImageByPost_Id(bookingDetail.getPost().getId());

                BookingDto dto = BookingDto.builder()
                        .bookingID(b.getId())
                        .postID(bookingDetail.getPost().getId())
                        .imagePost(postImages.get(0).getImageUrl())
                        .nameHost(bookingDetail.getPost().getHost().getUser().getUserDetail().getFullName())
                        .startDate(bookingDetail.getStartDate())
                        .endDate(bookingDetail.getEndDate())
                        .totalMoney(b.getTotalMoney())
                        .totalPerson(bookingDetail.getTotalPerson())
                        .status(b.getStatus())
                        .build();

                bookingDtoList.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), bookingDtoList));
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getAllBookingByHostID() {

        Host host = hostRepository.getHostsByUser_Id(SecurityUtils.getPrincipal().getId());

        if (Objects.isNull(host)) {
            throw new NotFoundException("Host khong ton tai");
        } else {
            List<BookingDetail> bookingDetails = bookingDetailRepository.getBookingDetailByPost_Host_Id(host.getId());

            List<BookingDto> bookingDtoList = new ArrayList<>();
            if (Objects.isNull(bookingDetails)) {
                throw new NotFoundException("Khong co bai booking nao lien quan den host_id nay");
            } else {

                bookingDetails.forEach(b -> {
                    List<PostImage> list = postImageRepository.findPostImageByPost_Id(b.getPost().getId());

                    BookingDto dto = BookingDto.builder()
                            .bookingID(b.getId())
                            .postID(b.getPost().getId())
                            .imagePost(list.get(0).getImageUrl())
                            .nameHost(b.getPost().getHost().getUser().getUserDetail().getFullName())
                            .startDate(b.getStartDate())
                            .endDate(b.getEndDate())
                            .totalMoney(b.getBooking().getTotalMoney())
                            .totalPerson(b.getTotalPerson())
                            .status(b.getBooking().getStatus())
                            .build();

                    bookingDtoList.add(dto);
                });
            }
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), bookingDtoList));
        }
    }

    @Override
    public ResponseEntity<JwtResponse> getAllBookingByAdmin() {

        List<Booking> list = bookingRepository.findAll();

        if (Objects.isNull(list)) {
            throw new NotFoundException("Khong co bai booking nao");
        } else {

            List<BookingDto> bookingDtoList = new ArrayList<>();

            list.forEach(b -> {

                BookingDetail bookingDetail = bookingDetailRepository.getBookingDetailByBooking_Id(b.getId());
                List<PostImage> postImages = postImageRepository.findPostImageByPost_Id(bookingDetail.getPost().getId());

                BookingDto dto = BookingDto.builder()
                        .bookingID(b.getId())
                        .postID(bookingDetail.getPost().getId())
                        .imagePost(postImages.get(0).getImageUrl())
                        .nameHost(bookingDetail.getPost().getHost().getUser().getUserDetail().getFullName())
                        .startDate(bookingDetail.getStartDate())
                        .endDate(bookingDetail.getEndDate())
                        .totalMoney(b.getTotalMoney())
                        .totalPerson(bookingDetail.getTotalPerson())
                        .status(b.getStatus())
                        .build();

                bookingDtoList.add(dto);
            });
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), bookingDtoList));
        }
    }
}
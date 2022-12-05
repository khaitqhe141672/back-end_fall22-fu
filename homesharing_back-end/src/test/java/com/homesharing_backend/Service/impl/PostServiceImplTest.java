package com.homesharing_backend.Service.impl;

import com.homesharing_backend.data.dto.PostDto;
import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.PostRequest;
import com.homesharing_backend.presentation.payload.request.PostServiceRequest;
import com.homesharing_backend.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.*;

class PostServiceImplTest {
    @Mock
    PostRepository postRepository;
    @Mock
    HostRepository hostRepository;
    @Mock
    DistrictRepository districtRepository;
    @Mock
    RoomTypeRepository roomTypeRepository;
    @Mock
    PostDetailRepository postDetailRepository;
    @Mock
    PostImageRepository postImageRepository;
    @Mock
    UtilityRepository utilityRepository;
    @Mock
    PostUtilityRepository postUtilityRepository;
    @Mock
    VoucherRepository voucherRepository;
    @Mock
    PostVoucherRepository postVoucherRepository;
    @Mock
    PaymentPackageRepository paymentPackageRepository;
    @Mock
    PostPaymentRepository postPaymentRepository;
    @Mock
    ProvinceRepository provinceRepository;
    @Mock
    ServicesRepository servicesRepository;
    @Mock
    PostServiceRepository postServiceRepository;
    @InjectMocks
    PostServiceImpl postServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetInterestingPlaceByPost() {
        when(postRepository.getPostTop()).thenReturn(List.of(new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0)));
        when(postImageRepository.getPostImageByPost_Id(anyLong(), any())).thenReturn(List.of(new PostImage(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), "imageUrl")));

        ResponseEntity<JwtResponse> result = postServiceImpl.getInterestingPlaceByPost();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetTopPostByRate() {
        when(postRepository.getTopPostByRate(any())).thenReturn(null);
        when(postImageRepository.getPostImageByPost_Id(anyLong(), any())).thenReturn(List.of(new PostImage(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), "imageUrl")));

        ResponseEntity<JwtResponse> result = postServiceImpl.getTopPostByRate();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCreatePosting() {
        when(hostRepository.getHostsByUser_Id(anyLong())).thenReturn(new Host(Long.valueOf(1), null, 0));
        when(districtRepository.getSearchDistrict(anyString(), anyLong())).thenReturn(new District(Long.valueOf(1), new Province(Long.valueOf(1), "name", "imageUrl"), "name"));
        when(roomTypeRepository.findRoomTypeById(anyLong())).thenReturn(null);
        when(utilityRepository.getUtilityById(anyLong())).thenReturn(new Utility(Long.valueOf(1), "name", "icon", new Host(Long.valueOf(1), null, 0)));
        when(voucherRepository.getVoucherById(anyLong())).thenReturn(new Voucher(Long.valueOf(1), new Host(Long.valueOf(1), null, 0), "code", "description", 0, 0, 0));
        when(provinceRepository.getProvincesByName(anyString())).thenReturn(new Province(Long.valueOf(1), "name", "imageUrl"));
        when(servicesRepository.getServicesById(anyLong())).thenReturn(new Services(Long.valueOf(1), "name", "icon"));

        ResponseEntity<ResponseObject> result = postServiceImpl.createPosting(new PostRequest(Long.valueOf(1), "address", 0, 0, 0, 0, "title", "description", 0f, List.of(Long.valueOf(1)), List.of(Long.valueOf(1)), Long.valueOf(1), "latitude", "longitude", List.of(new PostServiceRequest(Long.valueOf(1), 0f))));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAllPostByHost() {
        when(postRepository.getPostDTO(anyLong())).thenReturn(List.of(new PostDto(Long.valueOf(1), "title", "urlImage", Long.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), new GregorianCalendar(2022, Calendar.DECEMBER, 6, 2, 5).getTime(), new GregorianCalendar(2022, Calendar.DECEMBER, 6, 2, 5).getTime(), Double.valueOf(0), 0)));
        when(hostRepository.getHostsByUser_Id(anyLong())).thenReturn(new Host(Long.valueOf(1), null, 0));

        ResponseEntity<JwtResponse> result = postServiceImpl.getAllPostByHost();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testEditPost() {
        when(postRepository.getPostById(anyLong())).thenReturn(new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0));
        when(districtRepository.getSearchDistrict(anyString(), anyLong())).thenReturn(new District(Long.valueOf(1), new Province(Long.valueOf(1), "name", "imageUrl"), "name"));
        when(roomTypeRepository.findRoomTypeById(anyLong())).thenReturn(null);
        when(postDetailRepository.getPostDetailByPost_Id(anyLong())).thenReturn(new PostDetail(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), "address", new District(Long.valueOf(1), new Province(Long.valueOf(1), "name", "imageUrl"), "name"), "description", new RoomType(Long.valueOf(1), "name"), 0, 0, 0, 0, "longitude", "latitude"));
        when(utilityRepository.getUtilityById(anyLong())).thenReturn(new Utility(Long.valueOf(1), "name", "icon", new Host(Long.valueOf(1), null, 0)));
        when(postUtilityRepository.findPostUtilitiesByPost_Id(anyLong())).thenReturn(List.of(new PostUtility(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new Utility(Long.valueOf(1), "name", "icon", null), 0)));
        when(postUtilityRepository.getPostUtilityByPost_IdAndUtility_Id(anyLong(), anyLong())).thenReturn(new PostUtility(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new Utility(Long.valueOf(1), "name", "icon", new Host(Long.valueOf(1), null, 0)), 0));
        when(voucherRepository.getVoucherById(anyLong())).thenReturn(new Voucher(Long.valueOf(1), new Host(Long.valueOf(1), null, 0), "code", "description", 0, 0, 0));
        when(postVoucherRepository.getPostVoucherByPost_Id(anyLong())).thenReturn(List.of(new PostVoucher(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new Voucher(Long.valueOf(1), null, "code", "description", 0, 0, 0), null, null, 0)));
        when(postVoucherRepository.getPostVoucherByPost_IdAndVoucher_Id(anyLong(), anyLong())).thenReturn(new PostVoucher(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new Voucher(Long.valueOf(1), new Host(Long.valueOf(1), null, 0), "code", "description", 0, 0, 0), null, null, 0));
        when(provinceRepository.getProvincesByName(anyString())).thenReturn(new Province(Long.valueOf(1), "name", "imageUrl"));
        when(servicesRepository.getServicesById(anyLong())).thenReturn(new Services(Long.valueOf(1), "name", "icon"));
        when(postServiceRepository.getPostServicesByPost_Id(anyLong())).thenReturn(List.of(new PostServices(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new Services(Long.valueOf(1), "name", "icon"), 0f, 0)));
        when(postServiceRepository.getPostServicesByServices_IdAndPost_Id(anyLong(), anyLong())).thenReturn(new PostServices(Long.valueOf(1), new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0), new Services(Long.valueOf(1), "name", "icon"), 0f, 0));

        ResponseEntity<ResponseObject> result = postServiceImpl.editPost(Long.valueOf(1), new PostRequest(Long.valueOf(1), "address", 0, 0, 0, 0, "title", "description", 0f, List.of(Long.valueOf(1)), List.of(Long.valueOf(1)), Long.valueOf(1), "latitude", "longitude", List.of(new PostServiceRequest(Long.valueOf(1), 0f))));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testUpdateStatus() {
        when(postRepository.getPostById(anyLong())).thenReturn(new Post(Long.valueOf(1), null, "title", 0f, null, 0, 0));

        ResponseEntity<MessageResponse> result = postServiceImpl.updateStatus(Long.valueOf(1), 0);
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
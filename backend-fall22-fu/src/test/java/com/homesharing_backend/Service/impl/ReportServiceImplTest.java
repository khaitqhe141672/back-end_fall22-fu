package com.homesharing_backend.Service.impl;

import com.homesharing_backend.data.entity.*;
import com.homesharing_backend.data.repository.*;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.presentation.payload.request.ComplaintRequest;
import com.homesharing_backend.presentation.payload.request.ReportRequest;
import com.homesharing_backend.presentation.payload.request.UpdateReportPostRequest;
import com.homesharing_backend.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;

class ReportServiceImplTest {
    @Mock
    ReportRateRepository reportRateRepository;
    @Mock
    ReportPostRepository reportPostRepository;
    @Mock
    RateRepository rateRepository;
    @Mock
    PostRepository postRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    ReportTypeRepository reportTypeRepository;
    @Mock
    ComplaintPostRepository complaintPostRepository;
    @Mock
    ComplaintRateRepository complaintRateRepository;
    @Mock
    HostRepository hostRepository;
    @Mock
    PostImageRepository postImageRepository;
    @Mock
    HistoryHandleReportPostRepository historyHandleReportPostRepository;
    @Mock
    HistoryHandleReportPostDetailRepository historyHandleReportPostDetailRepository;
    @InjectMocks
    ReportServiceImpl reportServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReportRate() {
        when(rateRepository.getRateById(anyLong())).thenReturn(new Rate(Long.valueOf(1), null, 0, "comment", null, 0));
        when(reportTypeRepository.getReportTypeById(anyLong())).thenReturn(new ReportType(Long.valueOf(1), "name", 0));
        when(hostRepository.getHostsByUser_Id(anyLong())).thenReturn(new Host(Long.valueOf(1), null, 0));

        ResponseEntity<MessageResponse> result = reportServiceImpl.createReportRate(new ReportRequest("description", Long.valueOf(1)), Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCreateReportPost() {
        when(postRepository.getPostById(anyLong())).thenReturn(new Post(Long.valueOf(1), new Host(Long.valueOf(1), null, 0), "title", 0f, null, 0, 0));
        when(customerRepository.getCustomerByUser_Id(anyLong())).thenReturn(new Customer(Long.valueOf(1), new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0)));
        when(reportTypeRepository.getReportTypeById(anyLong())).thenReturn(new ReportType(Long.valueOf(1), "name", 0));

        ResponseEntity<MessageResponse> result = reportServiceImpl.createReportPost(new ReportRequest("description", Long.valueOf(1)), Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCreateComplaintPost() {
        when(postRepository.getPostById(anyLong())).thenReturn(new Post(Long.valueOf(1), new Host(Long.valueOf(1), null, 0), "title", 0f, null, 0, 0));
        when(hostRepository.getHostsByUser_Id(anyLong())).thenReturn(new Host(Long.valueOf(1), null, 0));
        when(historyHandleReportPostRepository.getHistoryHandleReportPostByIdAndPost_Id(anyLong(), anyLong())).thenReturn(new HistoryHandleReportPost(Long.valueOf(1), new Post(Long.valueOf(1), new Host(Long.valueOf(1), null, 0), "title", 0f, null, 0, 0), 0, 0));

        ResponseEntity<MessageResponse> result = reportServiceImpl.createComplaintPost(new ComplaintRequest("description"), Long.valueOf(1), Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testResolveComplaintPost() {
        when(postRepository.getPostById(anyLong())).thenReturn(new Post(Long.valueOf(1), new Host(Long.valueOf(1), null, 0), "title", 0f, null, 0, 0));
        when(complaintPostRepository.getComplaintPostById(anyLong())).thenReturn(new ComplaintPost(Long.valueOf(1), "description", new Host(Long.valueOf(1), null, 0), new Post(Long.valueOf(1), new Host(Long.valueOf(1), null, 0), "title", 0f, null, 0, 0), new HistoryHandleReportPost(Long.valueOf(1), new Post(Long.valueOf(1), new Host(Long.valueOf(1), null, 0), "title", 0f, null, 0, 0), 0, 0), 0, 0));

        ResponseEntity<MessageResponse> result = reportServiceImpl.resolveComplaintPost(Long.valueOf(1), 0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testResolveComplaintRate() {
        when(reportRateRepository.getReportRateById(anyLong())).thenReturn(new ReportRate(Long.valueOf(1), new ReportType(Long.valueOf(1), "name", 0), new Host(Long.valueOf(1), null, 0), new Rate(Long.valueOf(1), null, 0, "comment", null, 0), "description", 0));
        when(complaintRateRepository.getComplaintRateById(anyLong())).thenReturn(new ComplaintRate(Long.valueOf(1), new ReportRate(Long.valueOf(1), new ReportType(Long.valueOf(1), "name", 0), new Host(Long.valueOf(1), null, 0), new Rate(Long.valueOf(1), null, 0, "comment", null, 0), "description", 0), "description", new Host(Long.valueOf(1), null, 0), 0));

        ResponseEntity<MessageResponse> result = reportServiceImpl.resolveComplaintRate(Long.valueOf(1), 0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAllReportRateByAdmin() {
        ResponseEntity<ResponseObject> result = reportServiceImpl.getAllReportRateByAdmin(0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAllReportPostByAdmin() {
        ResponseEntity<ResponseObject> result = reportServiceImpl.getAllReportPostByAdmin(0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAllReportPostByHost() {
        when(reportPostRepository.getReportPostByPost_Id(anyLong())).thenReturn(List.of(new ReportPost(Long.valueOf(1), new ReportType(Long.valueOf(1), "name", 0), new Customer(Long.valueOf(1), new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0)), new Post(Long.valueOf(1), new Host(Long.valueOf(1), null, 0), "title", 0f, null, 0, 0), "description", 0)));

        ResponseEntity<JwtResponse> result = reportServiceImpl.getAllReportPostByHost(Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAllReportPostByPostOfHost() {
        when(reportPostRepository.countReportPostByPost_Id(anyLong())).thenReturn(0);
        when(reportPostRepository.listAllReportPostByHost(any())).thenReturn(null);
        when(reportPostRepository.getReportPostByPost_IdAndStatus(anyLong(), anyInt())).thenReturn(List.of(new ReportPost(Long.valueOf(1), new ReportType(Long.valueOf(1), "name", 0), new Customer(Long.valueOf(1), new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0)), new Post(Long.valueOf(1), new Host(Long.valueOf(1), null, 0), "title", 0f, null, 0, 0), "description", 0)));

        ResponseEntity<ResponseObject> result = reportServiceImpl.getAllReportPostByPostOfHost(0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAllDetailReportPostByPostIDOfHost() {
        when(reportPostRepository.findReportPostByPost_Id(anyLong(), any())).thenReturn(null);

        ResponseEntity<ResponseObject> result = reportServiceImpl.getAllDetailReportPostByPostIDOfHost(Long.valueOf(1), 0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAllComplaintRateByAdmin() {
        when(reportRateRepository.getReportRateById(anyLong())).thenReturn(new ReportRate(Long.valueOf(1), new ReportType(Long.valueOf(1), "name", 0), new Host(Long.valueOf(1), null, 0), new Rate(Long.valueOf(1), null, 0, "comment", null, 0), "description", 0));

        ResponseEntity<ResponseObject> result = reportServiceImpl.getAllComplaintRateByAdmin(0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAllComplaintPostByAdmin() {
        ResponseEntity<ResponseObject> result = reportServiceImpl.getAllComplaintPostByAdmin(0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAllComplaintPostByHost() {
        when(complaintPostRepository.getComplaintPostByHost_Id(anyLong(), any())).thenReturn(null);
        when(hostRepository.getHostsByUser_Id(anyLong())).thenReturn(new Host(Long.valueOf(1), null, 0));

        ResponseEntity<ResponseObject> result = reportServiceImpl.getAllComplaintPostByHost(0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testUpdateStatusReportRate() {
        when(reportRateRepository.getReportRateById(anyLong())).thenReturn(new ReportRate(Long.valueOf(1), new ReportType(Long.valueOf(1), "name", 0), new Host(Long.valueOf(1), null, 0), new Rate(Long.valueOf(1), null, 0, "comment", null, 0), "description", 0));

        ResponseEntity<MessageResponse> result = reportServiceImpl.updateStatusReportRate(Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testUpdateStatusReportPost() {
        when(reportPostRepository.getReportPostByIdAndPost_Id(anyLong(), anyLong())).thenReturn(new ReportPost(Long.valueOf(1), new ReportType(Long.valueOf(1), "name", 0), new Customer(Long.valueOf(1), new User(Long.valueOf(1), "username", "email", "password", new Role(Long.valueOf(1), ERole.ROLE_HOST), new UserDetail(Long.valueOf(1), "fullName", "mobile", "address", "avatarUrl", null), "codeActive", null, 0)), new Post(Long.valueOf(1), new Host(Long.valueOf(1), null, 0), "title", 0f, null, 0, 0), "description", 0));
        when(postRepository.getPostById(anyLong())).thenReturn(new Post(Long.valueOf(1), new Host(Long.valueOf(1), null, 0), "title", 0f, null, 0, 0));

        ResponseEntity<MessageResponse> result = reportServiceImpl.updateStatusReportPost(new UpdateReportPostRequest(List.of(Long.valueOf(1))), Long.valueOf(1), 0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAllReportPostStatusDoneByHost() {
        when(reportPostRepository.findReportPostByPost_IdAndStatus(anyLong(), anyInt(), any())).thenReturn(null);
        when(postRepository.getPostById(anyLong())).thenReturn(new Post(Long.valueOf(1), new Host(Long.valueOf(1), null, 0), "title", 0f, null, 0, 0));

        ResponseEntity<ResponseObject> result = reportServiceImpl.getAllReportPostStatusDoneByHost(0, Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAllHistoryReportPost() {
        when(historyHandleReportPostRepository.getHistoryHandleReportPostByPost_Id(anyLong(), any())).thenReturn(null);
        when(historyHandleReportPostDetailRepository.countHistoryHandleReportPostDetailByHistoryHandleReportPost_Id(anyLong())).thenReturn(0);

        ResponseEntity<ResponseObject> result = reportServiceImpl.getAllHistoryReportPost(Long.valueOf(1), 0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAllHistoryReportPostDetail() {
        when(historyHandleReportPostDetailRepository.getHistoryHandleReportPostDetailByHistoryHandleReportPost_Id(anyLong(), any())).thenReturn(null);

        ResponseEntity<ResponseObject> result = reportServiceImpl.getAllHistoryReportPostDetail(Long.valueOf(1), 0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCreateComplaintRate() {
        when(reportRateRepository.getReportRateById(anyLong())).thenReturn(new ReportRate(Long.valueOf(1), new ReportType(Long.valueOf(1), "name", 0), new Host(Long.valueOf(1), null, 0), new Rate(Long.valueOf(1), null, 0, "comment", null, 0), "description", 0));
        when(hostRepository.getHostsByUser_Id(anyLong())).thenReturn(new Host(Long.valueOf(1), null, 0));

        ResponseEntity<MessageResponse> result = reportServiceImpl.createComplaintRate(new ComplaintRequest("description"), Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
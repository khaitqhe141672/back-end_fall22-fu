package com.homesharing_backend.Voucher;

import com.homesharing_backend.data.dto.VoucherDto;
import com.homesharing_backend.data.entity.Host;
import com.homesharing_backend.data.entity.Utility;
import com.homesharing_backend.data.entity.Voucher;
import com.homesharing_backend.data.repository.HostRepository;
import com.homesharing_backend.data.repository.UtilityRepository;
import com.homesharing_backend.data.repository.VoucherRepository;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import com.homesharing_backend.security.services.UserDetailsImpl;
import com.homesharing_backend.service.impl.UtilityServiceImpl;
import com.homesharing_backend.service.impl.VoucherServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class VoucherController {

    @Mock
    HostRepository hostRepository;

    @Mock
    VoucherRepository voucherRepository;

    @Mock
    UtilityRepository utilityRepository;

    @InjectMocks
    UtilityServiceImpl utilityService;

    @InjectMocks
    VoucherServiceImpl voucherService;

    @BeforeAll
    public static void init() {
        UserDetailsImpl userDetails = new UserDetailsImpl(1l,"abc","abc","abc",null);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .thenReturn(userDetails);
    }

    @Test
    public void createVoucher(){

        Host getFromDatabase = new Host();
        getFromDatabase.setId(1l);

        Mockito.when(hostRepository.getHostsByUser_Id(1l)).thenReturn(getFromDatabase);

        // expect result

        Utility utilityReturnFromDB = Utility.builder()
                .id(1l)
                .name("khai")
                .host(getFromDatabase)
                .build();

        Mockito.lenient().when(utilityRepository.save(Mockito.any(Utility.class))).thenReturn(utilityReturnFromDB);

        ResponseEntity<Object> expectResult = ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Save success utility", new HashMap<>() {
            {
                put("utility", utilityReturnFromDB);
            }
        }));
        Assertions.assertEquals(expectResult, utilityService.insertUtility("khai"));

    }

    @Test
    public void getVoucher() throws Exception {
        Host getFromDatabase = new Host();
        getFromDatabase.setId(1l);

        Mockito.when(hostRepository.getHostsByUser_Id(Mockito.anyLong())).thenReturn(getFromDatabase);

        // expect result
        List<Voucher> voucherList = new ArrayList<>();
        Voucher voucher = new Voucher();
        voucher.setId(1l);
        voucherList.add(voucher);
        Mockito.when(voucherRepository.getVoucherByHost_IdAndStatus(getFromDatabase.getId(), 1)).thenReturn(voucherList);


        List<VoucherDto> voucherDTOS = new ArrayList<>();
        VoucherDto voucherDto = new VoucherDto();
        voucherDto.setIdVoucher(1l);
        voucherDTOS.add(voucherDto);
        ResponseEntity<Object> responseEntity =  ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), voucherDTOS));
        Assertions.assertEquals(responseEntity, voucherService.getAllVoucherByHostID());

    }
}

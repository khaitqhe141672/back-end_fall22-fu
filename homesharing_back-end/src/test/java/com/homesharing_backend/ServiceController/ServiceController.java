package com.homesharing_backend.ServiceController;

import com.homesharing_backend.data.dto.ServicesDto;
import com.homesharing_backend.data.entity.Services;
import com.homesharing_backend.data.repository.ServicesRepository;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.security.services.UserDetailsImpl;
import com.homesharing_backend.service.impl.ServicesServiceImpl;
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
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ServiceController {

    @Mock
    ServicesRepository servicesRepository;

    @InjectMocks
    ServicesServiceImpl servicesService;

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
    public void getAllServiceByHost(){
        List<Services> services = new ArrayList<>();
        Services s = new Services();
        s.setId(1l);
        services.add(s);

        Mockito.when(servicesRepository.findAll()).thenReturn(services);

        List<ServicesDto> dtoList = new ArrayList<>();
        ServicesDto dto = new ServicesDto();
        dto.setId(1l);
        dtoList.add(dto);

        ResponseEntity<Object> responseEntity =  ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), dtoList));
        Assertions.assertEquals(responseEntity, servicesService.getAllServiceByHost());
    }
}

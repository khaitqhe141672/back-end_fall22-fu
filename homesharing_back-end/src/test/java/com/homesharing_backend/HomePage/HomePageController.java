package com.homesharing_backend.HomePage;

import com.homesharing_backend.data.dto.HomeDto;
import com.homesharing_backend.data.dto.ProvinceDto;
import com.homesharing_backend.data.entity.Post;
import com.homesharing_backend.data.entity.Province;
import com.homesharing_backend.data.repository.PostImageRepository;
import com.homesharing_backend.data.repository.PostRepository;
import com.homesharing_backend.data.repository.ProvinceRepository;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.security.services.UserDetailsImpl;
import com.homesharing_backend.service.ProvinceService;
import com.homesharing_backend.service.impl.PostServiceImpl;
import com.homesharing_backend.service.impl.ProvinceServiceImpl;
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
public class HomePageController {

    @Mock
    private ProvinceRepository provinceRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostImageRepository postImageRepository;

    @InjectMocks
    private ProvinceServiceImpl provinceService;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeAll
    public static void init() {
        UserDetailsImpl userDetails = new UserDetailsImpl(1l, "abc", "abc", "abc", null);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .thenReturn(userDetails);
    }

    @Test
    public void getRecommendedPlaces() {

        List<Province> provinces = new ArrayList<>();
        Province province = new Province();
        province.setId(1l);
        provinces.add(province);

        Mockito.when(provinceRepository.getRecommendedPlacesByProvinces()).thenReturn(provinces);

        List<ProvinceDto> dtoList = new ArrayList<>();
        ProvinceDto dto = new ProvinceDto();
        dto.setProvinceID(1l);
        dtoList.add(dto);

        ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), dtoList));
        Assertions.assertEquals(responseEntity, provinceService.getRecommendedPlaces());
    }

    @Test
    public void getInterestingPlace() {
        List<Post> postList = new ArrayList<>();
        Post post = new Post();
        post.setId(1l);
        postList.add(post);

        Mockito.when(postRepository.getPostTop()).thenReturn(postList);

        List<HomeDto> homeDtoList = new ArrayList<>();
        HomeDto dto = new HomeDto();
        dto.setPostID(1l);
        homeDtoList.add(dto);

        ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), homeDtoList));
        Assertions.assertEquals(responseEntity, postService.getInterestingPlaceByPost());
    }

}

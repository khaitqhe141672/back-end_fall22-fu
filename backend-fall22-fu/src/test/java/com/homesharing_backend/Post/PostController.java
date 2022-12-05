package com.homesharing_backend.Post;


import com.homesharing_backend.HomesharingBackEndApplication;
import com.homesharing_backend.data.entity.PostServices;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = HomesharingBackEndApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PostController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostServices postServices;

    @Test
    public void getData() throws Exception {

        this.mockMvc.perform(get("/api/home/interesting-place")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

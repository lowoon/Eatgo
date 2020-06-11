package kr.co.fastcampus.eatgo.interfaces;

import static org.hamcrest.core.StringContains.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.fastcampus.eatgo.application.RegionService;
import kr.co.fastcampus.eatgo.domain.Region;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RegionController.class)
class RegionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegionService regionService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void list() throws Exception {
        List<Region> regions = new ArrayList<>();
        regions.add(Region.builder().name("Seoul").build());

        when(regionService.getRegions()).thenReturn(regions);

        mvc.perform(get("/regions")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Seoul")));
    }
}
package kr.co.fastcampus.eatgo.interfaces;

import static org.hamcrest.core.StringContains.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.fastcampus.eatgo.application.CategoryService;
import kr.co.fastcampus.eatgo.domain.Category;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void list() throws Exception {
        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(Category.builder().name("Korean Food").build());

        when(categoryService.getCategories()).thenReturn(mockCategories);

        mvc.perform(get("/categories"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Korean Food")));
    }
}
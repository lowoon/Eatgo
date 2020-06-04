package kr.co.fastcampus.eatgo.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.co.fastcampus.eatgo.domain.Category;
import kr.co.fastcampus.eatgo.domain.CategoryRepository;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void getCategories() {
        List<Category> mockCategories =  new ArrayList<>();
        mockCategories.add(Category.builder().name("Korean Food").build());

        given(categoryService.getCategories()).willReturn(mockCategories);

        Category category = categoryService.getCategories().get(0);
        assertThat(category.getName()).isEqualTo("Korean Food");
    }

    @Test
    void addCategory() {
        Category mockCategory = Category.builder().name("Korean Food").build();

        given(categoryService.addCategory(any(Category.class))).willReturn(mockCategory);

        Category category = categoryService.addCategory(mockCategory);
        assertThat(category.getName()).isEqualTo("Korean Food");
    }
}
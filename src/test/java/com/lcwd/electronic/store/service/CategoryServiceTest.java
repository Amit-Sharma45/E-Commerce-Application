package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.payload.PageableResponse;
import com.lcwd.electronic.store.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;

    Category category;

    @BeforeEach
    public void init() {
        category = Category.builder()
                .title("Mobile Phones")
                .description("this category contains mobile phones")
                .coverImage("abc.png")
                .build();
    }

    @Test
    public void createCategoryTest() {
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto category1 = categoryService.createCategory(modelMapper.map(category, CategoryDto.class));
        String actualTitle = category1.getTitle();
        System.out.println(actualTitle);
        String expectedTitle = "Mobile Phones";
        Assertions.assertEquals(expectedTitle, actualTitle);
        Assertions.assertNotNull(category1);
    }

    //    update category
    @Test
    public void updateCategoryTest() {
        String categoryId = "am45";
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setTitle("Leptops");
        categoryDto.setDescription("this category contains the various verities of leptops");
        categoryDto.setCoverImage("HP.png");
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto, categoryId);
        Assertions.assertEquals(categoryDto.getTitle(), categoryDto1.getTitle());
    }

    //delete category
    @Test
    public void deleteCategoryTest() {
        String categoryId = "an445";
        Mockito.when(categoryRepository.findById("an445")).thenReturn(Optional.of(category));

        categoryService.deleteCategory(categoryId);

        Mockito.verify(categoryRepository, Mockito.times(1)).delete(category);
    }

    // get all
    @Test
    public void getAllCategories() {
        Category category1 = Category.builder()
                .title("Leptops")
                .description("this category contains Leptops ")
                .coverImage("hp.png")
                .build();
        Category category2 = Category.builder()
                .title("Frezzs")
                .description("this category contains frezzes")
                .coverImage("wirphool.png")
                .build();
        Category category3 = Category.builder()
                .title("Mobile Phones")
                .description("this category contains mobile phones")
                .coverImage("abc.png")
                .build();

        List<Category> categories = Arrays.asList(category1, category2, category3);
        Page<Category> page = new PageImpl<>(categories);
        Mockito.when(categoryRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<CategoryDto> allCategories = categoryService.getAllCategories(1, 10, "title", "asc");
        Assertions.assertEquals(3, allCategories.getContent().size());
    }

    //get by id
    @Test
    public void getCategoryTest() {
        String categoryId = "am353n";
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryDto category1 = categoryService.getCategory(categoryId);
        System.out.println(category1.getTitle());
        Assertions.assertNotNull(category1);
        Assertions.assertEquals(category.getTitle(), category1.getTitle());

    }
}

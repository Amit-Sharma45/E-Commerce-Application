package com.lcwd.electronic.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.payload.PageableResponse;
import com.lcwd.electronic.store.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;
    private Category category;

    @BeforeEach
    public void init() {
        category = Category.builder()
                .title("Mobile Phones")
                .description("this category contains mobile phones")
                .coverImage("abc.png")
                .build();
    }

    private String convertObjectToJsonString(Object category) {
        try {
            return new ObjectMapper().writeValueAsString(category);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void createCategoryTest() throws Exception {
        CategoryDto dto = modelMapper.map(category, CategoryDto.class);

        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(dto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(category))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

    //   update category
    @Test
    public void updateCategoryTest() throws Exception {
        String categoryId = "am556n";
        CategoryDto dto = modelMapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.updateCategory(Mockito.any(), Mockito.anyString())).thenReturn(dto);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/categories/" + categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(category))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

    //    delete category
    @Test
    public void deleteCategoryTest() throws Exception {
        String categoryId = "am4453n";

        Mockito.doNothing().when(categoryService).deleteCategory(categoryId);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/categories/" + categoryId))
                .andDo(print())
                .andExpect(status().isOk());
    }

//    get all categories

    @Test
    public void getAllCategoriesTest() throws Exception {
        CategoryDto category1 = CategoryDto.builder()
                .title("Mobile Phones")
                .description("this category contains mobile phones")
                .coverImage("abc.png")
                .build();
        CategoryDto category2 = CategoryDto.builder()
                .title("Leptops")
                .description("this category contains leptops")
                .coverImage("hp.png")
                .build();
        CategoryDto category3 = CategoryDto.builder()
                .title("LEDs")
                .description("this category contains leds")
                .coverImage("led.png")
                .build();

        PageableResponse<CategoryDto> response = new PageableResponse<>();
        response.setContent(Arrays.asList(
                category1, category2, category3
        ));
        response.setLastPage(false);
        response.setPageNumber(1);
        response.setPageSize(5);
        response.setTotalPages(45);
        response.setTotalElements(400l);

        Mockito.when(categoryService.getAllCategories(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //    get single category
    @Test
    public void getSingleCategory() throws Exception {
        String categoryId = "am345n";
        CategoryDto dto = modelMapper.map(category, CategoryDto.class);

        Mockito.when(categoryService.getCategory(categoryId)).thenReturn(dto);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/categories/" + categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
//    create product with category

//    update category of product

//    get product of category

}

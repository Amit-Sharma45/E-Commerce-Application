package com.lcwd.electronic.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.payload.PageableResponse;
import com.lcwd.electronic.store.service.ProductService;
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
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @MockBean
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;

    private Product product;

    @BeforeEach
    public void init() {
        product = Product.builder()
                .title("Iphone 13 pro max")
                .description("This is the Good Mobile phone Launched by apple")
                .price(70000)
                .discountedPrice(65000)
                .quantity(100)
                .addedDate(new Date())
                .live(true)
                .stock(true)
                .productImageName("iphone.jpg")
                .build();
    }

    private String convertObjectToJsonString(Object product) {
        try {
            return new ObjectMapper().writeValueAsString(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //    create product
    @Test
    public void createProductTest() throws Exception {
        ProductDto dto = modelMapper.map(product, ProductDto.class);

        Mockito.when(productService.create(Mockito.any())).thenReturn(dto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

    //    update product
    @Test
    public void updateProductTest() throws Exception {
        String productId = "am445n";
        ProductDto dto = modelMapper.map(product, ProductDto.class);

        Mockito.when(productService.update(Mockito.any(), Mockito.anyString())).thenReturn(dto);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/products/" + productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

    //    delete product
    @Test
    public void deleteProductTest() throws Exception {
        String productId = "amn454";
        ProductDto dto = modelMapper.map(product, ProductDto.class);
        Mockito.doNothing().when(productService).delete(productId);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/products/" + productId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //    get product
    @Test
    public void getProductTest() throws Exception {
        String productId = "am645";
        ProductDto dto = modelMapper.map(product, ProductDto.class);
        Mockito.when(productService.get(productId)).thenReturn(dto);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/" + productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

    //    get all products
    @Test
    public void getAllProductsTest() throws Exception {
        ProductDto p1 = ProductDto.builder().title("Iphone 13 pro max").description("This is the Good Mobile phone Launched by apple").price(70000).discountedPrice(65000).quantity(100).addedDate(new Date()).live(true).stock(true).productImageName("iphone.jpg").build();
        ProductDto p2 = ProductDto.builder().title("Iphone 15 pro max").description("This is the Good Mobile phone Launched by apple").price(190000).discountedPrice(180000).quantity(60).addedDate(new Date()).live(false).stock(true).productImageName("iphone.jpg").build();
        ProductDto p3 = ProductDto.builder().title("Samsung s 23 ultra").description("This Mobile phone comes with superb camera Launched by samsung").price(140000).discountedPrice(134000).quantity(56).addedDate(new Date()).live(true).stock(true).productImageName("iphone.jpg").build();
        PageableResponse<ProductDto> response = new PageableResponse<>();
        response.setContent(Arrays.asList(p1, p2, p3));
        response.setPageNumber(1);
        response.setPageSize(10);
        response.setLastPage(false);
        response.setTotalPages(50);
        response.setTotalElements(450l);

        Mockito.when(productService.getAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    //    get all live products
    @Test
    public void getAllLiveProducts() throws Exception {
        ProductDto p1 = ProductDto.builder().title("Iphone 13 pro max").description("This is the Good Mobile phone Launched by apple").price(70000).discountedPrice(65000).quantity(100).addedDate(new Date()).live(true).stock(true).productImageName("iphone.jpg").build();
        ProductDto p2 = ProductDto.builder().title("Iphone 15 pro max").description("This is the Good Mobile phone Launched by apple").price(190000).discountedPrice(180000).quantity(60).addedDate(new Date()).live(false).stock(true).productImageName("iphone.jpg").build();
        ProductDto p3 = ProductDto.builder().title("Samsung s 23 ultra").description("This Mobile phone comes with superb camera Launched by samsung").price(140000).discountedPrice(134000).quantity(56).addedDate(new Date()).live(true).stock(true).productImageName("iphone.jpg").build();
        PageableResponse<ProductDto> response = new PageableResponse<>();
        response.setContent(Arrays.asList(p1, p3));
        response.setPageNumber(1);
        response.setPageSize(10);
        response.setLastPage(false);
        response.setTotalPages(50);
        response.setTotalElements(767l);
        Mockito.when(productService.getAllLive(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(response);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/live")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    //    search by title
    @Test
    public void searchByTitleTest() throws Exception {
        ProductDto p1 = ProductDto.builder().title("Iphone 13 pro max").description("This is the Good Mobile phone Launched by apple").price(70000).discountedPrice(65000).quantity(100).addedDate(new Date()).live(true).stock(true).productImageName("iphone.jpg").build();
        ProductDto p2 = ProductDto.builder().title("Iphone 15 pro max").description("This is the Good Mobile phone Launched by apple").price(190000).discountedPrice(180000).quantity(60).addedDate(new Date()).live(false).stock(true).productImageName("iphone.jpg").build();
        ProductDto p3 = ProductDto.builder().title("Samsung s 23 ultra").description("This Mobile phone comes with superb camera Launched by samsung").price(140000).discountedPrice(134000).quantity(56).addedDate(new Date()).live(true).stock(true).productImageName("iphone.jpg").build();
        PageableResponse<ProductDto> response = new PageableResponse<>();
        response.setContent(Arrays.asList(p1, p2));
        response.setPageNumber(1);
        response.setPageSize(10);
        response.setLastPage(false);
        response.setTotalPages(50);
        response.setTotalElements(767l);

        String subTitle = "Iphone";
        Mockito.when(productService.searchByTitle(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/search/" + subTitle)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
//    upload product image

//    serve product image


}

package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.payload.PageableResponse;
import com.lcwd.electronic.store.repositories.ProductRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ModelMapper modelMapper;
    @MockBean
    private ProductRepository productRepo;
    @Autowired
    private ProductService productService;

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
                .productImageName("iphone.png")
                .build();
    }

    //    create
    @Test
    public void createTest() {
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);
        ProductDto productDto = productService.create(modelMapper.map(product, ProductDto.class));

        String actualImageName = productDto.getProductImageName();
        System.out.println(actualImageName);
        String expectedImageName = "iphone.png";
        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(expectedImageName, actualImageName);
    }

    //    update
    @Test
    public void updateTest() {
        String productId = "am343n";
        ProductDto dto = new ProductDto();
        dto.setDescription("This is the superb phone of Apple launched in 2022");
        dto.setDiscountedPrice(60000);
        dto.setQuantity(45);
        dto.setLive(false);

        Mockito.when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);

        ProductDto updateProduct = productService.update(dto, productId);
        System.out.println(updateProduct.getQuantity());
        Assertions.assertEquals(dto.getQuantity(), updateProduct.getQuantity());
    }

    //    delete
    @Test
    public void deleteTest() {
        String productId = "am345n";
        Mockito.when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        productService.delete(productId);

        Mockito.verify(productRepo, Mockito.times(1)).delete(product);
    }

    //    get
    @Test
    public void getTest() {

        String productId = "am553n";
        Mockito.when(productRepo.findById(productId)).thenReturn(Optional.of(product));

        ProductDto dto = productService.get(productId);
        System.out.println(dto.getTitle());
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(product.getTitle(), dto.getTitle());
    }

    //    getAll
    @Test
    public void getAllTest() {
        Product p1 = Product.builder().title("Iphone 13 pro max").description("This is the Good Mobile phone Launched by apple").price(70000).discountedPrice(65000).quantity(100).addedDate(new Date()).live(true).stock(true).productImageName("iphone.png").build();
        Product p2 = Product.builder().title("Iphone 15 pro max").description("This is the Good Mobile phone Launched by apple").price(190000).discountedPrice(180000).quantity(50).addedDate(new Date()).live(true).stock(false).productImageName("iphone15.png").build();
        Product p3 = Product.builder().title("Samsung s23 ultra").description("This is the Good Mobile phone Launched by apple").price(150000).discountedPrice(134000).quantity(70).addedDate(new Date()).live(true).stock(true).productImageName("s23.png").build();

        List<Product> products = Arrays.asList(p1, p2, p3);
        Page<Product> page = new PageImpl<>(products);
        Mockito.when(productRepo.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> response = productService.getAll(1, 10, "title", "asc");
        Assertions.assertEquals(3, response.getContent().size());
    }

    //    getAll Live
    @Test
    public void getAllLiveTest() {
        Product p1 = Product.builder().title("Iphone 13 pro max").description("This is the Good Mobile phone Launched by apple").price(70000).discountedPrice(65000).quantity(100).addedDate(new Date()).live(true).stock(true).productImageName("iphone.png").build();
        Product p2 = Product.builder().title("Iphone 15 pro max").description("This is the Good Mobile phone Launched by apple").price(190000).discountedPrice(180000).quantity(50).addedDate(new Date()).live(false).stock(false).productImageName("iphone15.png").build();
        Product p3 = Product.builder().title("Samsung s23 ultra").description("This is the Good Mobile phone Launched by apple").price(150000).discountedPrice(134000).quantity(70).addedDate(new Date()).live(true).stock(true).productImageName("s23.png").build();

        List<Product> products = Arrays.asList(p1, p3);
        Page<Product> page = new PageImpl<>(products);
        Mockito.when(productRepo.findByLiveTrue((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> allLive = productService.getAllLive(1, 10, "title", "asc");
        Assertions.assertEquals(2, allLive.getContent().size());
    }

    //    search ByTitle
    @Test
    public void searchByTitleTest() {
        Product p1 = Product.builder().title("Iphone 13 pro max").description("This is the Good Mobile phone Launched by apple").price(70000).discountedPrice(65000).quantity(100).addedDate(new Date()).live(true).stock(true).productImageName("iphone.png").build();
        Product p2 = Product.builder().title("Iphone 15 pro max").description("This is the Good Mobile phone Launched by apple").price(190000).discountedPrice(180000).quantity(50).addedDate(new Date()).live(true).stock(false).productImageName("iphone15.png").build();
        Product p3 = Product.builder().title("Samsung s23 ultra").description("This is the Good Mobile phone Launched by apple").price(150000).discountedPrice(134000).quantity(70).addedDate(new Date()).live(true).stock(true).productImageName("s23.png").build();

        String subTitle = "Iphone";
        List<Product> products = Arrays.asList(p1, p2);
        Page<Product> page = new PageImpl<>(products);
        Mockito.when(productRepo.findByTitleContaining(Mockito.anyString(), (Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> response = productService.searchByTitle(subTitle, 1, 20, "price", "asc");
        Assertions.assertEquals(2, response.getContent().size());
    }
//    create With Category

//    update Category

//    get All Products of Category
}

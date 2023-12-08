package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.helper.AppConstants;
import com.lcwd.electronic.store.helper.UrlConstants;
import com.lcwd.electronic.store.payload.ApiResponse;
import com.lcwd.electronic.store.payload.ImageResponse;
import com.lcwd.electronic.store.payload.PageableResponse;
import com.lcwd.electronic.store.service.ImageService;
import com.lcwd.electronic.store.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(UrlConstants.PRODUCT_BASE_URL)
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ImageService imageService;
    @Value("${product.image.path}")
    private String imagePath;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    /**
     * @param productDto
     * @return ProductDto
     * @apiNote To create New Product
     * @author AMIT BHARDWAJ
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        logger.info("Entering the Request to create new Product");
        ProductDto productDto1 = productService.create(productDto);
        logger.info("Completed the Request to create new Product");
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    /**
     * @param productDto
     * @param productId
     * @return ProductDto
     * @apiNote To update the product
     * @author AMIT BHARDWAJ
     * @since 1.0
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {
        logger.info("Entering the Request to update the product with productId{}: ", productId);
        ProductDto updatedProduct = productService.update(productDto, productId);
        logger.info("Completed the Request to update the product with productId{}: ", productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    /**
     * @param productId
     * @return ApiResponse
     * @apiNote To delete the Product
     * @author AMIT BHARDWAJ
     * @since 1.0
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId) {
        logger.info("Entering the Request to delete the product with productId{}: ", productId);
        productService.delete(productId);
        ApiResponse response = ApiResponse.builder()
                .message(AppConstants.PRODUCT_DELETED)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        logger.info("Completed the Request to delete the product with productId{}: ", productId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * @param productId
     * @return ProductDto
     * @apiNote To get single Product
     * @author AMIT BHARDWAJ
     * @since 1.0
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
        logger.info("Entering the Request to get the product with productId{}: ", productId);
        ProductDto productDto = productService.get(productId);
        logger.info("Completed the Request to get the product with productId{}: ", productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return PageableResponse<ProductDto>
     * @apiNote To get All Products
     * @author AMIT BHARDWAJ
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        logger.info("Entering the Request to get all the products");
        PageableResponse<ProductDto> page = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed the Request to get all the products");
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return PageableResponse
     * @apiNote To get all Live products
     * @author AMIT BHARDWAJ
     * @since 1.0
     */
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProducts(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        logger.info("Entering the Request to get all live products");
        PageableResponse<ProductDto> page = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed the Request to get all live products");
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * @param subTitle
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return PageableResponse
     * @apiNote To Search the Product By Title
     * @author AMIT BHARDWAJ
     * @since 1.0
     */
    @GetMapping("/search/{subTitle}")
    public ResponseEntity<PageableResponse<ProductDto>> searchByTitle(
            @PathVariable String subTitle,
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        logger.info("Entering the Request to search the products by title");
        PageableResponse<ProductDto> page = productService.searchByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed the Request to search the products by title");
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * @param productId
     * @param image
     * @return ImageResponse
     * @throws IOException
     * @apiNote To upload the productImage
     * @author AMIT BHARDWAJ
     * @since 1.0
     */
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage") MultipartFile image
    ) throws IOException {
        logger.info("Entering the Request for upload product Image for productId{}: ", productId);
        String fileName = imageService.uploadImage(image, imagePath);
        ProductDto productDto = productService.get(productId);
        productDto.setProductImageName(fileName);
        ProductDto updatedProduct = productService.update(productDto, productId);
        ImageResponse response = ImageResponse.builder().imageName(updatedProduct.getProductImageName()).success(true).status(HttpStatus.CREATED).message("Product Image uploaded successfully !!").build();
        logger.info("Completed the Request for upload product Image for productId{}: ", productId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    /**
     * @param productId
     * @param response
     * @throws IOException
     * @apiNote To get the Product Image
     * @author AMIT BHARDWAJ
     * @since 1.0
     */
    @GetMapping("/image/{productId}")
    public void serveProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        logger.info("Entering the Request for get product Image for productId{}: ", productId);
        ProductDto productDto = productService.get(productId);
        logger.info("product image name : {} ", productDto.getProductImageName());
        InputStream resource = imageService.getResource(imagePath, productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        logger.info("Completed the Request for get product Image for productId{}: ", productId);
    }
}

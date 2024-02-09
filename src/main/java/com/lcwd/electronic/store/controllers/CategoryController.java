package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.helper.AppConstants;
import com.lcwd.electronic.store.helper.UrlConstants;
import com.lcwd.electronic.store.payload.ApiResponse;
import com.lcwd.electronic.store.payload.PageableResponse;
import com.lcwd.electronic.store.service.CategoryService;
import com.lcwd.electronic.store.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(UrlConstants.CATEGORY_BASE_URL)
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    /**
     * @param categoryDto
     * @return CategoryDto
     * @author AMIT SHARMA
     * @apiNote create new category
     * @since 1.0
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CategoryDto categoryDto) {
        logger.info("Entering the Request to create new Category");
        CategoryDto category = categoryService.createCategory(categoryDto);
        logger.info("Completed the Request to create new Category");
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * @param categoryDto
     * @param categoryId
     * @return CategoryDto
     * @author AMIT SHARMA
     * @apiNote To update the category
     * @since 1.0
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @Valid @RequestBody CategoryDto categoryDto,
            @PathVariable String categoryId) {
        logger.info("Entering the Request to update the Category with categoryId{}: ", categoryId);
        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto, categoryId);
        logger.info("Completed the Request to update the Category with categoryId{}: ", categoryId);
        return new ResponseEntity<>(categoryDto1, HttpStatus.OK);
    }

    /**
     * @param categoryId
     * @return ApiResponse
     * @apiNote Delete the category by categoryId
     * @author AMIT SHARMA
     * @since 1.0
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId) {
        logger.info("Entering the Request to delete the Category with categoryId{}: ", categoryId);
        categoryService.deleteCategory(categoryId);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(AppConstants.CATEGORY_DELETED)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        logger.info("Completed the Request to delete the Category with categoryId{}: ", categoryId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return PageableResponse<CategoryDto>
     * @author AMIT SHARMA
     * @apiNote To get all categories
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.CATEGORY_SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        logger.info("Entering the Request to get All Categories");
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed the Request to get All Categories");
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    /**
     * @param categoryId
     * @return CategoryDto
     * @author AMIT SHARMA
     * @apiNote To get Single Category
     * @since 1.0
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable String categoryId) {
        logger.info("Entering the Request to get category with categoryId{}: ", categoryId);
        CategoryDto category = categoryService.getCategory(categoryId);
        logger.info("Completed the Request to get category with categoryId{}: ", categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /**
     * @param categoryId
     * @param productDto
     * @return ProductDto
     * @apiNote To create Product with category
     * @author AMIT
     * @since 1.0
     */
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto productDto
    ) {
        logger.info("Entering the Request to create product with category of categoryId{}: ", categoryId);
        ProductDto productWithCategory = productService.createWithCategory(productDto, categoryId);
        logger.info("Completed the Request to create product with category of categoryId{}: ", categoryId);
        return new ResponseEntity<>(productWithCategory, HttpStatus.CREATED);

    }

    /**
     * @param categoryId
     * @param productId
     * @return ProductDto
     * @apiNote To update the category of product
     * @author AMIT
     * @since 1.0
     */
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(
            @PathVariable String categoryId,
            @PathVariable String productId
    ) {
        logger.info("Entering the Request to update category of product with productId{}: ", productId);
        ProductDto productDto = productService.updateCategory(productId, categoryId);
        logger.info("Completed the Request to update category of product with productId{}: ", productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);

    }

    /**
     * @param categoryId
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return PageableResponse of ProductDto
     * @apiNote To get all products of category
     * @author AMIT
     * @since 1.0
     */
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getProductOfCategory(
            @PathVariable String categoryId,
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        logger.info("Entering the Request to get products of category with categoryId{}: ", categoryId);
        PageableResponse<ProductDto> response = productService.getAllProductsOfCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed the Request to get products of category with categoryId{}: ", categoryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}

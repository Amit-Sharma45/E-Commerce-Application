package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.AppConstants;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.payload.PageableResponse;
import com.lcwd.electronic.store.repositories.CategoryRepository;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;
    @Value("${product.image.path}")
    private String productImagePath;

    @Override
    public ProductDto create(ProductDto productDto) {
        log.info("Initiating the dao call for save the product");
        Product product = modelMapper.map(productDto, Product.class);
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        product.setAddedDate(new Date());
        Product savedProduct = this.productRepository.save(product);
        log.info("Completed the dao call for save the product");
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        log.info("Initiating the dao call for update the product with productId{}: ", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());

        Product updatedProduct = productRepository.save(product);
        log.info("Completed the dao call for update the product with productId{}: ", productId);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        log.info("Initiating the dao call for delete the product with productId{}: ", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
        String fullPath = productImagePath + product.getProductImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException e) {
            log.info("product image not found in folder");
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        productRepository.delete(product);
        log.info("Completed the dao call for delete the product with productId{}: ", productId);
    }

    @Override
    public ProductDto get(String productId) {
        log.info("Initiating the dao call for get the product with productId{}: ", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
        log.info("Completed the dao call for get the product with productId{}: ", productId);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiating the dao call for get all products");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findAll(pageable);
        log.info("Completed the dao call for get all products");
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiating the dao call for get all live products");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        log.info("Completed the dao call for get all live products");
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiating the dao call for search the products by title");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable);
        log.info("Completed the dao call for search the products by title");
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        log.info("Initiating the dao call for create product of category with categoryId{}: ", categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND));
        Product product = modelMapper.map(productDto, Product.class);
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product savedProduct = this.productRepository.save(product);
        log.info("Completed the dao call for create product of category with categoryId{}: ", categoryId);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        log.info("Initiating the dao call for update category of product with productId{}: ", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND));
        product.setCategory(category);
        Product product1 = productRepository.save(product);
        log.info("Completed the dao call for update category of product with productId{}: ", productId);
        return modelMapper.map(product1, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProductsOfCategory(String categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiating the dao call for get all products of category with categoryId{}: ", categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND));
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByCategory(category, pageable);
        log.info("Completed the dao call for get all products of category with categoryId{}: ", categoryId);
        return Helper.getPageableResponse(page, ProductDto.class);
    }
}

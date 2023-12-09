package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.payload.PageableResponse;

public interface ProductService {

    //create
    ProductDto create(ProductDto productDto);

    //update
    ProductDto update(ProductDto productDto, String productId);

    //delete
    void delete(String productId);

    //get Single
    ProductDto get(String productId);

    //getAll
    PageableResponse<ProductDto> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //getAll:Live
    PageableResponse<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //search product
    PageableResponse<ProductDto> searchByTitle(String subTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    // create product with category
    ProductDto createWithCategory(ProductDto productDto, String categoryId);

    // update category of product
    ProductDto updateCategory(String productId, String categoryId);

    PageableResponse<ProductDto> getAllProductsOfCategory(String categoryId,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
}

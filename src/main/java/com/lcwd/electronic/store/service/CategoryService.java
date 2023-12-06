package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.payload.PageableResponse;

public interface CategoryService {

    //create
    CategoryDto createCategory(CategoryDto categoryDto);
    //update

    CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);

    //delete
    void deleteCategory(String categoryId);
    // get all

    PageableResponse<CategoryDto> getAllCategories(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
    //get single category detail

    CategoryDto getCategory(String categoryId);
    //search

}

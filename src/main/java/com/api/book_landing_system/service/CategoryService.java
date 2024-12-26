package com.api.book_landing_system.service;

import com.api.book_landing_system.dtos.category_dtos.CategoryGetDto;
import com.api.book_landing_system.dtos.category_dtos.CategoryPostPutRespDto;
import com.api.book_landing_system.dtos.category_dtos.CategoryPostDto;

import java.util.List;

public interface CategoryService {
    CategoryPostPutRespDto createCategory(CategoryPostDto postDto);
    List<CategoryGetDto> getAllCategories();
    CategoryPostPutRespDto updateCategory(Long id, CategoryPostDto postDto);
}

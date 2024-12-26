package com.api.book_landing_system.mapper;

import com.api.book_landing_system.dtos.category_dtos.CategoryGetDto;
import com.api.book_landing_system.dtos.category_dtos.CategoryPostPutRespDto;
import com.api.book_landing_system.dtos.category_dtos.CategoryPostDto;
import com.api.book_landing_system.model.Category;


public class CategoryMapper {
    public static Category categoryPostDtoToCategory(CategoryPostDto categoryPostDto){
        return Category.builder()
                .name(categoryPostDto.getName())
                .build();
    }

    public static CategoryPostPutRespDto categoryToCategoryPostPutRespDto(Category category){
        return CategoryPostPutRespDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static CategoryPostPutRespDto categoryToCategoryPostPutRespDto(Category category, String message){
        return CategoryPostPutRespDto.builder()
                .id(category.getId())
                .name(category.getName())
                .message(message)
                .build();
    }

    public static CategoryGetDto categoryToCategoryGetDto(Category category){
        return CategoryGetDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}

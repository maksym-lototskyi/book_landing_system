package com.api.book_landing_system.service_impl;

import com.api.book_landing_system.dtos.category_dtos.CategoryGetDto;
import com.api.book_landing_system.dtos.category_dtos.CategoryPostPutRespDto;
import com.api.book_landing_system.dtos.category_dtos.CategoryPostDto;
import com.api.book_landing_system.mapper.CategoryMapper;
import com.api.book_landing_system.model.Category;
import com.api.book_landing_system.repository.CategoryRepository;
import com.api.book_landing_system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryPostPutRespDto createCategory(CategoryPostDto postDto) {
        verifyThatCategoryDoesNotExistByName(postDto);

        Category saved = categoryRepository.save(CategoryMapper.categoryPostDtoToCategory(postDto));

        return CategoryMapper.categoryToCategoryPostPutRespDto(saved, "Category was created successfully");
    }

    @Override
    public List<CategoryGetDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryMapper::categoryToCategoryGetDto).collect(Collectors.toList());
    }

    @Override
    public CategoryPostPutRespDto updateCategory(Long categoryId, CategoryPostDto postDto) {
        if(!categoryRepository.existsById(categoryId)){
            throw new IllegalArgumentException("There is no category with id " + categoryId);
        }
        verifyThatCategoryDoesNotExistByName(postDto);

        Category category = categoryRepository.findById(categoryId).get();
        category.setName(postDto.getName());

        Category saved = categoryRepository.save(category);

        return CategoryMapper.categoryToCategoryPostPutRespDto(saved);
    }

    private void verifyThatCategoryDoesNotExistByName(CategoryPostDto postDto){
        if(categoryRepository.existsByName(postDto.getName())){
            throw new IllegalArgumentException("Category with that name already exists");
        }
    }
}

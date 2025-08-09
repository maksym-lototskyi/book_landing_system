package com.api.book_landing_system.controller;

import com.api.book_landing_system.dtos.category_dtos.CategoryGetDto;
import com.api.book_landing_system.dtos.category_dtos.CategoryPostDto;
import com.api.book_landing_system.dtos.category_dtos.CategoryPostPutRespDto;
import com.api.book_landing_system.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryPostPutRespDto> addCategory(@Valid @RequestBody CategoryPostDto postDto){
        CategoryPostPutRespDto response = categoryService.createCategory(postDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryPostPutRespDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryPostDto postDto){
        CategoryPostPutRespDto response = categoryService.updateCategory(id, postDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryGetDto>> retrieveAllCategories(){
        List<CategoryGetDto> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}

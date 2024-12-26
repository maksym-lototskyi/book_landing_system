package com.api.book_landing_system.service_impl;

import com.api.book_landing_system.dtos.category_dtos.CategoryPostDto;
import com.api.book_landing_system.dtos.category_dtos.CategoryPostPutRespDto;
import com.api.book_landing_system.model.Category;
import com.api.book_landing_system.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;
    private CategoryPostDto postDto;
    private Category testCategory;
    @BeforeEach
    public void setUp(){
        postDto = CategoryPostDto.builder().name("Action").build();
        testCategory = Category.builder().id(1L).name("Action").build();
    }

    @Test
    public void testThatCreateCategoryThrowsExceptionIfCategoryWithThatNameAlreadyExists(){
        when (categoryRepository.existsByName(any(String.class))).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> categoryService.createCategory(postDto));
        verify(categoryRepository, times(1)).existsByName(any(String.class));
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void testCreateCategorySuccess(){
        when(categoryRepository.existsByName(any(String.class))).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        CategoryPostPutRespDto result = categoryService.createCategory(postDto);

        assertNotNull(result);
        assertEquals(result.getName(), testCategory.getName());

        verify(categoryRepository, times(1)).existsByName(any(String.class));
        verify(categoryRepository).save(any(Category.class));
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void testThatUpdateCategoryThrowsExceptionIfCategoryNotExists(){
        when(categoryRepository.existsById(any(Long.class))).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> categoryService.updateCategory(1L, postDto));
        verify(categoryRepository, times(1)).existsById(any(Long.class));
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void testThatUpdateCategoryThrowsExceptionIfCategoryWithThatNameAlreadyExists(){
        when(categoryRepository.existsById(any(Long.class))).thenReturn(true);
        when(categoryRepository.existsByName(any(String.class))).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> categoryService.updateCategory(1L, postDto));

        verify(categoryRepository, times(1)).existsById(any(Long.class));
        verify(categoryRepository, times(1)).existsByName(any(String.class));
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void testUpdateCategorySuccess(){
        postDto.setName("Romance");
        testCategory.setName("Action");

        when(categoryRepository.existsById(any(Long.class))).thenReturn(true);
        when(categoryRepository.existsByName(any(String.class))).thenReturn(false);
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(testCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        CategoryPostPutRespDto result = categoryService.updateCategory(1L, postDto);

        assertNotNull(result);
        assertEquals(result.getName(), postDto.getName());
        verify(categoryRepository, times(1)).existsById(any(Long.class));

        verify(categoryRepository, times(1)).existsByName(any(String.class));
        verify(categoryRepository).findById(any(Long.class));
        verify(categoryRepository).save(any(Category.class));
        verifyNoMoreInteractions(categoryRepository);

    }
}
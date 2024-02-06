package com.rootapp.services;

import java.util.List;

import com.rootapp.payloads.CategoryDto;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, long categoryId);

    CategoryDto getCategoryById(long categoryId);

    List<CategoryDto> getAllCategories();

    void deleteCategory(long categoryId);

}

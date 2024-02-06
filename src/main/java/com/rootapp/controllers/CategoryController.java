package com.rootapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rootapp.payloads.ApiResponse;
import com.rootapp.payloads.CategoryDto;
import com.rootapp.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // POST -> create category
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

        CategoryDto createCategoryDto = this.categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
    }

    // PUT -> update category
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
            @PathVariable Long categoryId) {

        CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto, categoryId);

        return ResponseEntity.ok(updatedCategoryDto);
    }

    // GET -> fetch single category
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long categoryId) {

        CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);

        return ResponseEntity.ok(categoryDto);
    }

    // GET -> fetch all categories
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {

        List<CategoryDto> categoryDtos = this.categoryService.getAllCategories();

        return ResponseEntity.ok(categoryDtos);
    }

    // DELETE -> delete category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {

        this.categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok(ApiResponse.builder().message("category deleted successfully").success(true).build());
    }

}

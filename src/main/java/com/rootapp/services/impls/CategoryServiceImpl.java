package com.rootapp.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rootapp.entities.Category;
import com.rootapp.exceptions.ResourceNotFoundException;
import com.rootapp.payloads.CategoryDto;
import com.rootapp.repositories.CategoryRepository;
import com.rootapp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    // create category
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category createdCategory = this.categoryRepository.save(this.dtoToCategory(categoryDto));

        return this.categoryToDto(createdCategory);
    }

    // update category
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long categoryId) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());

        Category updatedCategory = this.categoryRepository.save(category);

        return this.categoryToDto(updatedCategory);
    }

    // get category by id
    @Override
    public CategoryDto getCategoryById(long categoryId) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        return this.categoryToDto(category);
    }

    // get all categories
    @Override
    public List<CategoryDto> getAllCategories() {

        List<CategoryDto> categories = this.categoryRepository.findAll().stream()
                .map(category -> this.categoryToDto(category)).collect(Collectors.toList());

        return categories;
    }

    // delete category by id
    @Override
    public void deleteCategory(long categoryId) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        this.categoryRepository.delete(category);
    }

    // convert category entity to category dto
    public CategoryDto categoryToDto(Category category) {

        return modelMapper.map(category, CategoryDto.class);
    }

    // convert category dto to category entity
    public Category dtoToCategory(CategoryDto categoryDto) {

        return modelMapper.map(categoryDto, Category.class);
    }

}

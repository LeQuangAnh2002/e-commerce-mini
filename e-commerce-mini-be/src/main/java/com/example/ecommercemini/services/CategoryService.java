package com.example.ecommercemini.services;

import com.example.ecommercemini.dtos.CategoryDto;
import com.example.ecommercemini.dtos.PageableResponse;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);

    void deleteCategory(int categoryId);

    CategoryDto getCategoryById(int categoryId);

    PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir);
}

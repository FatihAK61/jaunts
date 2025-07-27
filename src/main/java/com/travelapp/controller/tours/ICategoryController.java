package com.travelapp.controller.tours;

import com.travelapp.dto.tours.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryController {

    ResponseEntity<CategoryDto> createCategory(CategoryDto categoryDto);

    ResponseEntity<CategoryDto> updateCategory(Long id, CategoryDto categoryDto);

    ResponseEntity<Page<CategoryDto>> getAllCategories(Pageable pageable);

    ResponseEntity<List<CategoryDto>> getAllCategoriesList();

    ResponseEntity<List<CategoryDto>> getActiveCategories();

    ResponseEntity<List<CategoryDto>> getRootCategories();

    ResponseEntity<List<CategoryDto>> getActiveRootCategories();

    ResponseEntity<CategoryDto> getCategoryById(Long id);

    ResponseEntity<CategoryDto> getCategoryByName(String name);

    ResponseEntity<List<CategoryDto>> getCategoryChildren(Long id);

    ResponseEntity<List<CategoryDto>> searchCategories(String q);

    ResponseEntity<Void> deleteCategory(Long id);

    ResponseEntity<CategoryDto> toggleCategoryStatus(Long id);
}

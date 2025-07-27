package com.travelapp.services.tours;

import com.travelapp.dto.tours.CategoryDto;
import com.travelapp.models.tours.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();

    Page<CategoryDto> getAllCategories(Pageable pageable);

    List<CategoryDto> getActiveCategories();

    List<CategoryDto> getRootCategories();

    List<CategoryDto> getActiveRootCategories();

    CategoryDto getCategoryByName(String name);

    List<CategoryDto> getCategoryChildren(Long parentId);

    List<CategoryDto> searchCategories(String searchTerm);

    CategoryDto toggleCategoryStatus(Long id);

    CategoryDto convertToDto(Category category);

    CategoryDto convertToDtoWithChildren(Category category);

    boolean isDescendant(Category ancestor, Category potential);

    CategoryDto getCategoryById(Long id);

    void deleteCategory(Long id);
}

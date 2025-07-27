package com.travelapp.services.impl.tours;

import com.travelapp.dto.tours.CategoryDto;
import com.travelapp.helper.errorhandler.DuplicateResourceException;
import com.travelapp.helper.errorhandler.InvalidOperationException;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.models.tours.Category;
import com.travelapp.repository.tours.CategoryRepository;
import com.travelapp.services.impl.tours.mappers.CategoryMapperService;
import com.travelapp.services.tours.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapperService categoryMapperService;

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new DuplicateResourceException("Category with name '" + categoryDto.getName() + "' already exists");
        }

        Category category = categoryMapperService.toEntity(categoryDto);

        if (categoryDto.getParentId() != null && categoryRepository.existsById(categoryDto.getParentId())) {
            Category parent = categoryRepository.findById(categoryDto.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found with name: ", categoryDto.getParentId().toString()));
            category.setParent(parent);
        }

        Category savedCategory = categoryRepository.save(category);

        return categoryMapperService.toDto(savedCategory);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: ", id.toString()));

        // Check if name is being changed and if new name already exists
        if (!existingCategory.getName().equals(categoryDto.getName()) &&
                categoryRepository.existsByNameAndIdNot(categoryDto.getName(), id)) {
            throw new DuplicateResourceException("Category with name '" + categoryDto.getName() + "' already exists");
        }

        // Prevent setting parent to itself or its descendants to avoid circular reference
        if (categoryDto.getParentId() != null) {
            if (categoryDto.getParentId().equals(id)) {
                throw new InvalidOperationException("Category cannot be its own parent");
            }

            Category newParent = categoryRepository.findById(categoryDto.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found with id: ", categoryDto.getParentId().toString()));

            if (isDescendant(existingCategory, newParent)) {
                throw new InvalidOperationException("Cannot set descendant category as parent");
            }

            existingCategory.setParent(newParent);
        } else {
            existingCategory.setParent(null);
        }

        // Update fields
        existingCategory.setName(categoryDto.getName());
        existingCategory.setDescription(categoryDto.getDescription());
        existingCategory.setIconUrl(categoryDto.getIconUrl());
        existingCategory.setActive(categoryDto.getActive());

        Category updatedCategory = categoryRepository.save(existingCategory);

        return convertToDto(updatedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDto> getAllCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getActiveCategories() {
        List<Category> categories = categoryRepository.findByActiveTrue();
        return categories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getRootCategories() {
        List<Category> categories = categoryRepository.findRootCategories();
        return categories.stream()
                .map(this::convertToDtoWithChildren)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getActiveRootCategories() {
        List<Category> categories = categoryRepository.findActiveRootCategories();
        return categories.stream()
                .map(this::convertToDtoWithChildren)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryByName(String name) {
        Category category = categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with name: ", name));
        return convertToDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategoryChildren(Long parentId) {
        if (!categoryRepository.existsById(parentId)) {
            throw new ResourceNotFoundException("Parent category not found with id: ", parentId.toString());
        }

        List<Category> children = categoryRepository.findByParentId(parentId);
        return children.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> searchCategories(String searchTerm) {
        List<Category> categories = categoryRepository.findByNameContainingIgnoreCaseAndActiveTrue(searchTerm);
        return categories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto toggleCategoryStatus(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: ", id.toString()));

        category.setActive(!category.getActive());
        Category updatedCategory = categoryRepository.save(category);

        return convertToDto(updatedCategory);
    }

    @Override
    public CategoryDto convertToDto(Category category) {
        return categoryMapperService.toDto(category);
    }

    @Override
    public CategoryDto convertToDtoWithChildren(Category category) {
        CategoryDto dto = convertToDto(category);

        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            List<CategoryDto> childrenDtos = category.getChildren().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            dto.setChildren(childrenDtos);
        }

        return dto;
    }

    @Override
    public boolean isDescendant(Category ancestor, Category potential) {
        Category current = potential.getParent();
        while (current != null) {
            if (current.getId().equals(ancestor.getId())) {
                return true;
            }
            current = current.getParent();
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: ", id.toString()));
        return convertToDtoWithChildren(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: ", id.toString()));

        // Check if category has children
        if (categoryRepository.hasChildren(id)) {
            throw new InvalidOperationException("Cannot delete category that has child categories");
        }

        // Check if category has tours
        Long tourCount = categoryRepository.countToursByCategoryId(id);
        if (tourCount > 0) {
            throw new InvalidOperationException("Cannot delete category that has associated tours");
        }

        categoryRepository.delete(category);
    }
}

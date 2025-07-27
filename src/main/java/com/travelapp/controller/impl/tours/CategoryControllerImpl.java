package com.travelapp.controller.impl.tours;

import com.travelapp.controller.tours.ICategoryController;
import com.travelapp.dto.tours.CategoryDto;
import com.travelapp.services.tours.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryControllerImpl implements ICategoryController {

    private final ICategoryService categoryService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable(name = "id") Long id, @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<CategoryDto>> getAllCategories(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
        Page<CategoryDto> categories = categoryService.getAllCategories(pageable);
        return ResponseEntity.ok(categories);
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategoriesList() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @Override
    @GetMapping("/active")
    public ResponseEntity<List<CategoryDto>> getActiveCategories() {
        List<CategoryDto> categories = categoryService.getActiveCategories();
        return ResponseEntity.ok(categories);
    }

    @Override
    @GetMapping("/root")
    public ResponseEntity<List<CategoryDto>> getRootCategories() {
        List<CategoryDto> categories = categoryService.getRootCategories();
        return ResponseEntity.ok(categories);
    }

    @Override
    @GetMapping("/root/active")
    public ResponseEntity<List<CategoryDto>> getActiveRootCategories() {
        List<CategoryDto> categories = categoryService.getActiveRootCategories();
        return ResponseEntity.ok(categories);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "id") Long id) {
        CategoryDto category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @Override
    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDto> getCategoryByName(@PathVariable(name = "name") String name) {
        CategoryDto category = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(category);
    }

    @Override
    @GetMapping("/{id}/children")
    public ResponseEntity<List<CategoryDto>> getCategoryChildren(@PathVariable(name = "id") Long id) {
        List<CategoryDto> children = categoryService.getCategoryChildren(id);
        return ResponseEntity.ok(children);
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<List<CategoryDto>> searchCategories(@RequestParam String q) {
        List<CategoryDto> categories = categoryService.searchCategories(q);
        return ResponseEntity.ok(categories);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<CategoryDto> toggleCategoryStatus(@PathVariable(name = "id") Long id) {
        CategoryDto updatedCategory = categoryService.toggleCategoryStatus(id);
        return ResponseEntity.ok(updatedCategory);
    }
}

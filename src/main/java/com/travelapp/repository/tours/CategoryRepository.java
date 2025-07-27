package com.travelapp.repository.tours;

import com.travelapp.models.tours.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    @Query("SELECT c FROM Category c JOIN FETCH c.tours WHERE c.id = :id")
    Optional<Category> getCategoryWithTours(@Param("id") Long id);

    // Find by name
    Optional<Category> findByName(String name);

    // Find by name ignoring case
    Optional<Category> findByNameIgnoreCase(String name);

    // Check if category exists by name
    boolean existsByName(String name);

    // Check if category exists by name and not current id (for updates)
    boolean existsByNameAndIdNot(String name, Long id);

    // Find all active categories
    List<Category> findByActiveTrue();

    // Find all active categories with pagination
    Page<Category> findByActiveTrue(Pageable pageable);

    // Find root categories (categories without parent)
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    List<Category> findRootCategories();

    // Find root categories with pagination
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    Page<Category> findRootCategories(Pageable pageable);

    // Find active root categories
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL AND c.active = true")
    List<Category> findActiveRootCategories();

    // Find children of a specific category
    List<Category> findByParentId(Long parentId);

    // Find active children of a specific category
    List<Category> findByParentIdAndActiveTrue(Long parentId);

    // Find categories by name containing
    List<Category> findByNameContainingIgnoreCase(String name);

    // Find active categories by name containing
    List<Category> findByNameContainingIgnoreCaseAndActiveTrue(String name);

    // Count tours in category
    @Query("SELECT COUNT(t) FROM Tour t WHERE t.category.id = :categoryId")
    Long countToursByCategoryId(@Param("categoryId") Long categoryId);

    // Find categories with tours count
    @Query("SELECT c, COUNT(t) FROM Category c LEFT JOIN c.tours t GROUP BY c")
    List<Object[]> findCategoriesWithToursCount();

    // Check if category has children
    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.parent.id = :categoryId")
    boolean hasChildren(@Param("categoryId") Long categoryId);

    // Get category hierarchy (with all descendants)
    @Query(value = """
            WITH RECURSIVE category_hierarchy AS (
                SELECT id, name, description, icon_url, active, parent_id, 0 as level
                FROM categories 
                WHERE id = :categoryId
            
                UNION ALL
            
                SELECT c.id, c.name, c.description, c.icon_url, c.active, c.parent_id, ch.level + 1
                FROM categories c
                INNER JOIN category_hierarchy ch ON c.parent_id = ch.id
            )
            SELECT * FROM category_hierarchy ORDER BY level, name
            """, nativeQuery = true)
    List<Object[]> getCategoryHierarchy(@Param("categoryId") Long categoryId);
}

package lt.techin.exam.category.service;

import lt.techin.exam.category.dto.CategoryCreateRequest;
import lt.techin.exam.category.dto.CategoryResponse;
import lt.techin.exam.category.dto.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryCreateRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse updateCategory(Long id, CategoryUpdateRequest request);

    void deleteCategory(Long id);
}
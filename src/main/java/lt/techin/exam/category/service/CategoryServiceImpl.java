package lt.techin.exam.category.service;

import lombok.RequiredArgsConstructor;
import lt.techin.exam.category.dto.CategoryCreateRequest;
import lt.techin.exam.category.dto.CategoryResponse;
import lt.techin.exam.category.dto.CategoryUpdateRequest;
import lt.techin.exam.category.mapper.CategoryMapper;
import lt.techin.exam.category.model.Category;
import lt.techin.exam.category.repository.CategoryRepository;
import lt.techin.exam.exception.customexception.CategoryDuplicateException;
import lt.techin.exam.exception.customexception.CategoryNotFoundException;
import lt.techin.exam.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final RecipeRepository recipeRepository;

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new CategoryDuplicateException(request.name());
        }

        Category category = categoryMapper.toEntity(request);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.toResponse(findCategoryById(id));
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
        Category category = findCategoryById(id);

        if (!category.getName().equals(request.name())
                && categoryRepository.existsByName(request.name())) {
            throw new CategoryDuplicateException(request.name());
        }

        category.setName(request.name());
        category.setDescription(request.description());

        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = findCategoryById(id);
        if (recipeRepository.existsByCategory(category)) {
            categoryRepository.delete(category);
        }
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}
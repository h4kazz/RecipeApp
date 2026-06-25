// src/main/java/lt/techin/exam/recipe/service/RecipeServiceImpl.java
package lt.techin.exam.recipe.service;

import lombok.RequiredArgsConstructor;
import lt.techin.exam.category.model.Category;
import lt.techin.exam.category.repository.CategoryRepository;
import lt.techin.exam.exception.customexception.CategoryNotFoundException;
import lt.techin.exam.exception.customexception.RecipeNotFoundException;
import lt.techin.exam.exception.customexception.UserNotFoundException;
import lt.techin.exam.recipe.dto.RecipeCreateRequest;
import lt.techin.exam.recipe.dto.RecipeResponse;
import lt.techin.exam.recipe.dto.RecipeUpdateRequest;
import lt.techin.exam.recipe.mapper.RecipeMapper;
import lt.techin.exam.recipe.model.Recipe;
import lt.techin.exam.recipe.repository.RecipeRepository;
import lt.techin.exam.recipe.repository.RecipeSpecifications;
import lt.techin.exam.user.model.User;
import lt.techin.exam.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public RecipeResponse createRecipe(RecipeCreateRequest request, String username) {
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));

        Recipe recipe = recipeMapper.toEntity(request, category, creator);
        Recipe saved = recipeRepository.save(recipe);
        return recipeMapper.toResponse(saved);
    }

    @Override
    public Page<RecipeResponse> getAllRecipes(
            String categoryName,
            Long userId,
            Pageable pageable
    ) {
        Specification<Recipe> spec = RecipeSpecifications.withFilters(categoryName, userId);
        return recipeRepository.findAll(spec, pageable)
                .map(recipeMapper::toResponse);
    }

    @Override
    public RecipeResponse getRecipeById(Long id) {
        return recipeMapper.toResponse(findRecipeById(id));
    }

    @Override
    public RecipeResponse updateRecipe(Long id, RecipeUpdateRequest request, String username) {
        Recipe recipe = findRecipeById(id);
        if (!recipe.getCreatedBy().getUsername().equals(username)) {
            throw new AccessDeniedException("You can only update your own recipes");
        }

        recipe.setTitle(request.title());
        recipe.setIngredients(new ArrayList<>(request.ingredients()));
        recipe.setInstructions(new ArrayList<>(request.instructions()));
        recipe.setRating(request.rating());

        Recipe saved = recipeRepository.save(recipe);
        return recipeMapper.toResponse(saved);
    }

    @Override
    public void deleteRecipe(Long id, String username, boolean isAdmin) {
        Recipe recipe = findRecipeById(id);
        boolean isOwner = recipe.getCreatedBy().getUsername().equals(username);
        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("You can only delete your own recipes");
        }
        recipeRepository.delete(recipe);
    }


    private Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
    }
}
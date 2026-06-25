package lt.techin.exam.recipe.service;

import lt.techin.exam.recipe.dto.RecipeCreateRequest;
import lt.techin.exam.recipe.dto.RecipeResponse;
import lt.techin.exam.recipe.dto.RecipeUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeService {

    RecipeResponse createRecipe(RecipeCreateRequest request, String username);

    Page<RecipeResponse> getAllRecipes(
            String categoryName,
            Long userId,
            Pageable pageable
    );

    RecipeResponse getRecipeById(Long id);

    RecipeResponse updateRecipe(Long id, RecipeUpdateRequest request, String username);

    void deleteRecipe(Long id, String username, boolean isAdmin);
}
package lt.techin.exam.recipe.mapper;

import lt.techin.exam.category.model.Category;
import lt.techin.exam.recipe.dto.RecipeCreateRequest;
import lt.techin.exam.recipe.dto.RecipeResponse;
import lt.techin.exam.recipe.model.Recipe;
import lt.techin.exam.user.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RecipeMapper {

    public Recipe toEntity(RecipeCreateRequest request, Category category, User creator) {
        return Recipe.builder()
                .title(request.title())
                .ingredients(new ArrayList<>(request.ingredients()))
                .instructions(new ArrayList<>(request.instructions()))
                .rating(request.rating())
                .category(category)
                .createdBy(creator)
                .build();
    }

    public RecipeResponse toResponse(Recipe recipe) {
        return new RecipeResponse(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getIngredients(),
                recipe.getInstructions(),
                recipe.getRating(),
                recipe.getCategory().getId(),
                recipe.getCategory().getName(),
                recipe.getCreatedBy().getId(),
                recipe.getCreatedBy().getUsername(),
                recipe.getCreatedAt(),
                recipe.getUpdatedAt()
        );
    }
}
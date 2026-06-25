package lt.techin.exam.recipe.dto;

import java.time.LocalDateTime;
import java.util.List;

public record RecipeResponse(
        Long id,
        String title,
        List<String> ingredients,
        List<String> instructions,
        int rating,
        Long categoryId,
        String categoryName,
        Long createdById,
        String createdByUsername,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
package lt.techin.exam.recipe.dto;


import jakarta.validation.constraints.*;

import java.util.List;

public record RecipeCreateRequest(
        @NotBlank @Size(min = 3, max = 100) String title,
        @NotEmpty List<@NotBlank String> ingredients,
        @NotEmpty List<@NotBlank String> instructions,
        @Min(1) @Max(5) int rating,
        @NotNull Long categoryId
) {}
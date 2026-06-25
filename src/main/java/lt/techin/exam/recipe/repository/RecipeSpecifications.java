// src/main/java/lt/techin/exam/recipe/repository/RecipeSpecifications.java
package lt.techin.exam.recipe.repository;

import lt.techin.exam.recipe.model.Recipe;
import org.springframework.data.jpa.domain.Specification;

public final class RecipeSpecifications {

    private RecipeSpecifications() {}

    public static Specification<Recipe> withFilters(String categoryName, Long userId) {
        Specification<Recipe> spec = (root, query, cb) -> cb.conjunction();

        if (categoryName != null) {
            spec = spec.and(hasCategoryName(categoryName));
        }
        if (userId != null) {
            spec = spec.and(hasCreatedByUserId(userId));
        }
        return spec;
    }

    public static Specification<Recipe> hasCategoryName(String categoryName) {
        return (root, query, cb) -> cb.like(
                cb.lower(root.get("category").get("name")),
                "%" + categoryName.toLowerCase() + "%"
        );
    }

    public static Specification<Recipe> hasCreatedByUserId(Long userId) {
        return (root, query, cb) -> cb.equal(root.get("createdBy").get("id"), userId);
    }
}
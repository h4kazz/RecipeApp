package lt.techin.exam.recipe.repository;

import lt.techin.exam.category.model.Category;
import lt.techin.exam.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RecipeRepository
        extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

    boolean existsByCategory(Category category);
}
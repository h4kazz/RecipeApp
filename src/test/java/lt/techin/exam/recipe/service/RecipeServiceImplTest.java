// src/test/java/lt/techin/exam/recipe/service/RecipeServiceImplTest.java
package lt.techin.exam.recipe.service;

import lt.techin.exam.category.model.Category;
import lt.techin.exam.category.repository.CategoryRepository;
import lt.techin.exam.exception.customexception.RecipeNotFoundException;
import lt.techin.exam.recipe.mapper.RecipeMapper;
import lt.techin.exam.recipe.model.Recipe;
import lt.techin.exam.recipe.repository.RecipeRepository;
import lt.techin.exam.user.model.User;
import lt.techin.exam.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private RecipeMapper recipeMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private User owner;
    private Recipe recipe;

    @BeforeEach
    void setUp() {
        owner = User.builder()
                .id(1L)
                .username("alice")
                .build();

        Category category = Category.builder()
                .id(1L)
                .name("Desertai")
                .build();

        recipe = Recipe.builder()
                .id(10L)
                .title("Sokoladinis tortas")
                .rating(4)
                .category(category)
                .createdBy(owner)
                .build();
    }

    @Test
    @DisplayName("deleteRecipe: savininkas gali pasalinti savo recepta")
    void deleteRecipe_ownerCanDeleteOwnRecipe() {

        when(recipeRepository.findById(10L)).thenReturn(Optional.of(recipe));

        recipeService.deleteRecipe(10L, "alice", false);

        verify(recipeRepository).delete(recipe);
    }

    @Test
    @DisplayName("deleteRecipe: admin gali pasalinti bet kuri recepta")
    void deleteRecipe_adminCanDeleteAnyRecipe() {
        when(recipeRepository.findById(10L)).thenReturn(Optional.of(recipe));

        recipeService.deleteRecipe(10L, "bob", true);

        verify(recipeRepository).delete(recipe);
    }

    @Test
    @DisplayName("deleteRecipe: ne savininkas ir ne admin gauna AccessDeniedException")
    void deleteRecipe_whenNotOwnerAndNotAdmin_throwsAccessDenied() {
        when(recipeRepository.findById(10L)).thenReturn(Optional.of(recipe));

        assertThrows(
                AccessDeniedException.class,
                () -> recipeService.deleteRecipe(10L, "bob", false)
        );

        verify(recipeRepository, never()).delete(any(Recipe.class));
    }

    @Test
    @DisplayName("deleteRecipe: kai recepto nera, meta RecipeNotFoundException")
    void deleteRecipe_whenRecipeNotFound_throwsRecipeNotFound() {
        when(recipeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(
                RecipeNotFoundException.class,
                () -> recipeService.deleteRecipe(999L, "alice", false)
        );

        verify(recipeRepository, never()).delete(any(Recipe.class));
    }
}
package lt.techin.exam.exception.customexception;

import lt.techin.exam.exception.defaultexception.DefaultDuplicateException;

public class RecipeNotFoundException extends DefaultDuplicateException {
    public RecipeNotFoundException(Long id) {
        super("Recipe not found with id: " + id);
    }
}

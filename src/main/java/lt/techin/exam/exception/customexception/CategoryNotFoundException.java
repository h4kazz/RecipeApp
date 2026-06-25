package lt.techin.exam.exception.customexception;

import lt.techin.exam.exception.defaultexception.DefaultNotFoundException;

public class CategoryNotFoundException extends DefaultNotFoundException {
    public CategoryNotFoundException(Long id) {
        super("Category by id " + id + " not found");
    }
}

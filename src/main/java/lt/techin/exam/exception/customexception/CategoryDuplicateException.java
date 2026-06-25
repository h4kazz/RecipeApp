package lt.techin.exam.exception.customexception;

import lt.techin.exam.exception.defaultexception.DefaultDuplicateException;

public class CategoryDuplicateException extends DefaultDuplicateException {
    public CategoryDuplicateException(String categoryName) {
        super("Category by name: " + categoryName + " already exist");
    }
}

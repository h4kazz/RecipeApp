package lt.techin.exam.exception.customexception;

public class CategoryInUseException extends RuntimeException {
    public CategoryInUseException(String categoryName) {
        super("Category by name: " + categoryName + " already exist");
    }
}

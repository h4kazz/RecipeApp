package lt.techin.exam.exception.defaultexception;

public class DefaultNotFoundException extends RuntimeException {
    public DefaultNotFoundException(String message) {
        super(message);
    }
}

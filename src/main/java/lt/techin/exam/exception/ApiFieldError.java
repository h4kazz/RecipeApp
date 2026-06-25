package lt.techin.exam.exception;

public record ApiFieldError(
        String field,
        String message
) {
}

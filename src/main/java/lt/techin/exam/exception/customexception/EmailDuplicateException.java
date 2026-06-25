package lt.techin.exam.exception.customexception;

import lt.techin.exam.exception.defaultexception.DefaultDuplicateException;

public class EmailDuplicateException extends DefaultDuplicateException {
    public EmailDuplicateException(String email) {
        super("Email: " + email + " is already taken");
    }
}

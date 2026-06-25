package lt.techin.exam.exception.customexception;

import lt.techin.exam.exception.defaultexception.DefaultDuplicateException;

public class UsernameDuplicateException extends DefaultDuplicateException {
    public UsernameDuplicateException(String username) {
        super("Username: " + username + " is already taken");
    }
}

package lt.techin.exam.exception.customexception;

import lt.techin.exam.exception.defaultexception.DefaultNotFoundException;

public class UserNotFoundException extends DefaultNotFoundException {
    public UserNotFoundException(String username) {
        super("User with username: " + username + " not found");
    }
}

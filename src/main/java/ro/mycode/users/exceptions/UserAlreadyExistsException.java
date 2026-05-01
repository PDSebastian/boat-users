package ro.mycode.users.exceptions;

import ro.mycode.system.constants.ErrorConstants;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {

        super(ErrorConstants.USER_ALREADY_EXISTS_ERROR_MESSAGE);
    }
}

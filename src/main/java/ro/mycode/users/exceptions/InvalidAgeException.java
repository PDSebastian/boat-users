package ro.mycode.users.exceptions;

import ro.mycode.system.constants.ErrorConstants;

public class InvalidAgeException extends RuntimeException {
    public InvalidAgeException() {

        super(ErrorConstants.INVALID_AGE_ERROR_MESSAGE);
    }
}

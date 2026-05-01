package ro.mycode.users.exceptions;

import ro.mycode.system.constants.ErrorConstants;

import javax.lang.model.type.ErrorType;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {

        super(ErrorConstants.INVALID_EMAIL_ERROR_MESSAGE);
    }
}

package ro.mycode.users.exceptions;

import ro.mycode.system.constants.ErrorConstants;

public class UserNotFoundexception extends RuntimeException {
    public UserNotFoundexception()
    {
        super(ErrorConstants.USER_NOT_FOUND_ERROR_MESSAGE);
    }
}

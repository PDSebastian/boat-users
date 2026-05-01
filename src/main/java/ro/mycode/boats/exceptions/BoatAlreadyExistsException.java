package ro.mycode.boats.exceptions;

import ro.mycode.system.constants.ErrorConstants;

public class BoatAlreadyExistsException extends RuntimeException {
    public BoatAlreadyExistsException()
    {
        super(ErrorConstants.BOAT_ALREADY_EXISTS_ERROR_MESSAGE);
    }
}

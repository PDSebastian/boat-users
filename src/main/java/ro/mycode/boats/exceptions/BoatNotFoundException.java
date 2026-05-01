package ro.mycode.boats.exceptions;

import ro.mycode.system.constants.ErrorConstants;

public class BoatNotFoundException extends RuntimeException {
    public BoatNotFoundException() {

        super(ErrorConstants.BOAT_NOT_FOUND_ERROR_MESSAGE);
    }
}

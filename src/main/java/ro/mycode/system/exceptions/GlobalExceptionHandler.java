package ro.mycode.system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.mycode.boats.exceptions.BoatAlreadyExistsException;
import ro.mycode.boats.exceptions.BoatNotFoundException;
import ro.mycode.users.exceptions.UserAlreadyExistsException;
import ro.mycode.users.exceptions.UserNotFoundexception;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            BoatNotFoundException.class,
            UserNotFoundexception.class,

    })
    public ResponseEntity<ApiErrorResponse> Exception(RuntimeException ex){
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiErrorResponse);



    }
    @ExceptionHandler({
            BoatAlreadyExistsException.class,
            UserAlreadyExistsException.class
    })
    public ResponseEntity<ApiErrorResponse> Exception(Exception ex){
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiErrorResponse);
    }
}

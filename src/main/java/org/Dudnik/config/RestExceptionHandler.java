package org.Dudnik.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {


    @ResponseBody
    @ExceptionHandler(value = { UserNotFoundException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String  handleUserNotFoundException(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage());
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(value = { UserAlreadyExistException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String  handleUserExistException(UserAlreadyExistException ex) {
        log.error(ex.getMessage());
        return ex.getMessage();
    }

}

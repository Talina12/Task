package org.Dudnik.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityExistsException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User exist")
public class UserAlreadyExistException extends EntityExistsException {

    public UserAlreadyExistException(String string) {
        super(string);
    }


}

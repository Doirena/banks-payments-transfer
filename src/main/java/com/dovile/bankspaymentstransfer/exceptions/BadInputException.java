package com.dovile.bankspaymentstransfer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadInputException extends Exception {

    private static final long serialVersionUID = 1L;

    public BadInputException(String message) {
        super(message);
    }
}

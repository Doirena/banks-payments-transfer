package com.dovile.bankspaymentstransfer.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadInputException extends Exception {

    private static final long serialVersionUID = 1L;

    public BadInputException(String message) {
        super(message);
    }
}

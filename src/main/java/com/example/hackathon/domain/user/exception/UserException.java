package com.example.hackathon.domain.user.exception;

import com.example.hackathon.domain.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class UserException extends ApplicationException {

    protected UserException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}

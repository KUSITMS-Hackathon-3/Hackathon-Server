package com.example.hackathon.global.config.security.exception;

import com.example.hackathon.domain.user.exception.UserException;

import static com.example.hackathon.global.config.security.constant.SecurityConstants.SecurityExceptionList.REQUIRED_LOGGED_IN;

public class RequiredLoggedinException extends UserException {
    public RequiredLoggedinException() {
        super(REQUIRED_LOGGED_IN.getErrorCode(),
                REQUIRED_LOGGED_IN.getHttpStatus(),
                REQUIRED_LOGGED_IN.getMessage()
        );
    }
}

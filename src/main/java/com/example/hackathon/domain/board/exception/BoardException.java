package com.example.hackathon.domain.board.exception;

import com.example.hackathon.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class BoardException extends ApplicationException {
    protected BoardException(String errorCode, HttpStatus httpStatus, String message){
        super(errorCode, httpStatus, message);
    }
}

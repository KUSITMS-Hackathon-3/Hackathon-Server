package com.example.hackathon.domain.user.exception;

import com.example.hackathon.domain.user.constant.UserConstants;
import com.example.hackathon.domain.user.constant.UserConstants.UserExceptionList;

public class NotFoundUserIdException extends UserException{
    public NotFoundUserIdException(){
        super(UserExceptionList.NOT_FOUND_EMAIL.getErrorCode(),
                UserExceptionList.NOT_FOUND_EMAIL.getHttpStatus(),
                UserExceptionList.NOT_FOUND_EMAIL.getMessage());
    }
}

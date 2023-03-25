package com.example.hackathon.domain.user.exception;

import com.example.hackathon.domain.user.constant.UserConstants.UserExceptionList;

public class OverlapUserException extends UserException{
    public OverlapUserException(){
        super(UserExceptionList.OVERLAP_USER.getErrorCode(),
                UserExceptionList.OVERLAP_USER.getHttpStatus(),
                UserExceptionList.OVERLAP_USER.getMessage());
    }
}


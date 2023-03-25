package com.example.hackathon.domain.comment.exception;

import org.springframework.http.HttpStatus;

import static com.example.hackathon.domain.comment.constant.CommentConstants.CommentExceptionList.NOT_FOUND_COMMENT;

public class NotFoundCommentException extends CommentException {
    public NotFoundCommentException() {
        super(NOT_FOUND_COMMENT.getErrorCode(),
                NOT_FOUND_COMMENT.getHttpStatus(),
                NOT_FOUND_COMMENT.getMessage()
        );
    }
}


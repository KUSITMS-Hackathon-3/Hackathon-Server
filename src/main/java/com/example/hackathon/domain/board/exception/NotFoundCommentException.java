package com.example.hackathon.domain.board.exception;

import com.example.hackathon.domain.board.constant.BoardConstants;
import com.example.hackathon.domain.comment.constant.CommentConstants;

import static com.example.hackathon.domain.comment.constant.CommentConstants.CommentExceptionList.NOT_FOUND_COMMENT;

public class NotFoundCommentException extends CommentException {
    public NotFoundCommentException() {
        super(NOT_FOUND_COMMENT.getErrorCode(),
                NOT_FOUND_COMMENT.getHttpStatus(),
                NOT_FOUND_COMMENT.getMessage()
        );
    }
}

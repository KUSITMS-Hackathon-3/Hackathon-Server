package com.example.hackathon.domain.board.exception;

import com.example.hackathon.domain.board.constant.BoardConstants;

import static com.example.hackathon.domain.board.constant.BoardConstants.BoardExceptionList.NOT_FOUND_BOARD;

public class NotFoundBoardException extends BoardException {
    public NotFoundBoardException(){
        super(NOT_FOUND_BOARD.getErrorCode(),NOT_FOUND_BOARD.getHttpStatus(), NOT_FOUND_BOARD.getMessage());
    }
}

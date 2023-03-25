package com.example.hackathon.domain.board.exception;

import com.example.hackathon.domain.board.constant.BoardConstants;

import static com.example.hackathon.domain.board.constant.BoardConstants.BoardExceptionList.NOT_BOARD_WRITER;

public class NotBoardWriterException extends BoardException{
    public NotBoardWriterException(){
        super(NOT_BOARD_WRITER.getErrorCode(),NOT_BOARD_WRITER.getHttpStatus(), NOT_BOARD_WRITER.getMessage());
    }
}

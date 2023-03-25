package com.example.hackathon.domain.certification.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class CertificationConstant {

    @Getter
    @RequiredArgsConstructor
    public enum ECommentResponseMessage{
        SAVE_SUCCESS("인증에 성공했습니다."),
        FIND_DATE_SUCCESS("하루 조회에 성공했습니다."),
        FIND_ALL_DATE_SUCCESS("전체 날짜 조회에 성공했습니다.");
        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    public enum CommentExceptionList {
        NOT_FOUND_COMMENT("C0001", HttpStatus.NOT_FOUND, "해당 commentId로 Comment를 찾을 수 없습니다.");

        private final String errorCode;
        private final HttpStatus httpStatus;
        private final String message;
    }
}

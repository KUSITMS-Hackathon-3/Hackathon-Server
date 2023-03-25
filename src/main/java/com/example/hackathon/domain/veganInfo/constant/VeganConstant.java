package com.example.hackathon.domain.veganInfo.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class VeganConstant {

    @Getter
    @RequiredArgsConstructor
    public enum EBoardResponseMessage{
        SELECT_SUCCESS("조회에 성공했습니다.");
        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    public enum UserExceptionList {
        NOT_FOUND_EMAIL("U0001", HttpStatus.NOT_FOUND, "해당 아이디를 가진 유저가 존재하지 않습니다"),
        NOT_FOUND_PASSWORD("U0002", HttpStatus.NOT_FOUND, "비밀번호가 잘못되었습니다."),
        OVERLAP_USER("U0003", HttpStatus.BAD_REQUEST, "이미 존재하는 회원입니다");

        private final String errorCode;
        private final HttpStatus httpStatus;
        private final String message;
    }

    @Getter
    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }
}

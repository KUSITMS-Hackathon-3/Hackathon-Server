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
}

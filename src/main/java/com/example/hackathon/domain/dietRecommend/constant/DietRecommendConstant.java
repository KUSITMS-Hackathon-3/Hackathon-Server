package com.example.hackathon.domain.dietRecommend.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class DietRecommendConstant {
    @Getter
    @RequiredArgsConstructor
    public enum EBoardResponseMessage{
        SELECT_SUCCESS("조회에 성공했습니다.");
        private final String message;
    }

    @Getter
    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }
}

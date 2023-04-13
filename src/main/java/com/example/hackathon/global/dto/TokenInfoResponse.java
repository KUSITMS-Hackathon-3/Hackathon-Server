package com.example.hackathon.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TokenInfoResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpirationTime;

    /**
     * 이걸 여기 추가하는건 맘에 안들긴 한데 어떻게 해야할지 생각해보자
     */
    private Long userIdx;

    public static TokenInfoResponse from(String grantType, String accessToken, String refreshToken, Long accessTokenExpirationTime, Long userIdx) {
        return TokenInfoResponse.builder()
                .grantType(grantType)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpirationTime(accessTokenExpirationTime)
                .userIdx(userIdx)
                .build();
    }
}


package com.example.hackathon.domain.user.dto;

import com.example.hackathon.domain.global.dto.TokenInfoResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

public abstract class UserDto {

    @Getter
    @AllArgsConstructor
    @Builder
    @ApiModel(description = "회원가입을 위한 요청객체")
    public static class SignupRequest {
        private String userId;
        private String nickName;
        private String password;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @ApiModel(description = "로그인을 위한 요청객체")
    public static class LoginRequest {
        @NotBlank(message = "아이디를 입력해주세요")
        @ApiModelProperty(notes = "아이디를 입력해주세요")
        private String userId;

        @NotBlank(message = "비밀번호를 입력해주세요")
        @ApiModelProperty(notes = "비밀번호를 입력해주세요")
        private String password;
    }

    @Getter
    @Builder
    @ApiModel(description = "로그인을 위한 응답객체")
    public static class LoginResponse {
        private String accessToken;
        private String refreshToken;

        public static LoginResponse from(TokenInfoResponse tokenInfoResponse) {
            return LoginResponse.builder()
                    .accessToken(tokenInfoResponse.getAccessToken())
                    .refreshToken(tokenInfoResponse.getRefreshToken())
                    .build();
        }
    }
}

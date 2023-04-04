package com.example.hackathon.domain.user.controller;

import com.example.hackathon.global.dto.ResponseDto;
import com.example.hackathon.domain.user.constant.UserConstants;
import com.example.hackathon.domain.user.dto.UserDto.LoginRequest;
import com.example.hackathon.domain.user.dto.UserDto.LoginResponse;
import com.example.hackathon.domain.user.dto.UserDto.SignupRequest;
import com.example.hackathon.domain.user.service.UserService;
import com.example.hackathon.global.dto.TokenInfoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
@Api(tags = "User API")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> singupUser(@Valid @RequestBody SignupRequest signupRequest) {
        this.userService.signup(signupRequest);
        return ResponseEntity.ok(ResponseDto.create(UserConstants.EBoardResponseMessage.SIGNUP_SUCCESS.getMessage()));
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponse>> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = this.userService.login(loginRequest);
            return ResponseEntity.ok(ResponseDto.create(UserConstants.EBoardResponseMessage.LOGIN_SUCCESS.getMessage(), loginResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.ok(ResponseDto.create(UserConstants.UserExceptionList.NOT_FOUND_PASSWORD.getMessage()));
        }
    }
    /**
     * 아이디 비밀번호 일치하지 않는경우 체크하도록 만들었어
     */

    @PostMapping("/reIssueAccessToken/{userIdx}")
    public ResponseEntity<ResponseDto<LoginResponse>> reIssueAccessToken(@PathVariable Long userIdx){
        LoginResponse loginResponse = this.userService.reIssueToken(userIdx);
        return ResponseEntity.ok(ResponseDto.create(UserConstants.EBoardResponseMessage.REISSUE_SUCCESS.getMessage(), loginResponse));
    }
}

package com.example.hackathon.domain.user.controller;

import com.example.hackathon.domain.global.dto.ResponseDto;
import com.example.hackathon.domain.user.constant.UserConstants;
import com.example.hackathon.domain.user.dto.UserDto;
import com.example.hackathon.domain.user.dto.UserDto.LoginRequest;
import com.example.hackathon.domain.user.dto.UserDto.LoginResponse;
import com.example.hackathon.domain.user.dto.UserDto.SignupRequest;
import com.example.hackathon.domain.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
@Api(tags = "User API")
@Slf4j
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> singupUser(@Valid @ModelAttribute SignupRequest signupRequest) {
        this.userService.signup(signupRequest);
        return ResponseEntity.ok(ResponseDto.create(UserConstants.EBoardResponseMessage.SIGNUP_SUCCESS.getMessage()));
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponse>> loginUser(@Valid @ModelAttribute LoginRequest loginRequest) {
        LoginResponse loginResponse = this.userService.login(loginRequest);
        return ResponseEntity.ok(ResponseDto.create(UserConstants.EBoardResponseMessage.LOGIN_SUCCESS.getMessage(),loginResponse));
    }
}

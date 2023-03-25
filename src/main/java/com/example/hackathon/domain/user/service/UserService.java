package com.example.hackathon.domain.user.service;

import com.example.hackathon.domain.user.dto.UserDto;
import com.example.hackathon.domain.user.dto.UserDto.LoginRequest;
import com.example.hackathon.domain.user.dto.UserDto.LoginResponse;
import com.example.hackathon.domain.user.entity.User;

public interface UserService {

    void signup(UserDto.SignupRequest signupRequest);
    LoginResponse login(LoginRequest loginRequest);
    User validateUserId(String userId);
}

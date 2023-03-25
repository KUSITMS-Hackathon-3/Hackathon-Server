package com.example.hackathon.domain.user.service;

import com.example.hackathon.global.config.security.jwt.JwtTokenProvider;
import com.example.hackathon.global.dto.TokenInfoResponse;
import com.example.hackathon.domain.user.dto.UserDto;
import com.example.hackathon.domain.user.dto.UserDto.LoginRequest;
import com.example.hackathon.domain.user.entity.User;
import com.example.hackathon.domain.user.exception.NotFoundUserIdException;
import com.example.hackathon.domain.user.exception.OverlapUserException;
import com.example.hackathon.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.hackathon.domain.user.constant.UserConstants.Role.ROLE_USER;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider tokenProvider;

    @Override
    public void signup(UserDto.SignupRequest signupRequest) {
        this.validateOverlap(signupRequest.getUserId());
        User user= User.builder()
                .userId(signupRequest.getUserId())
                .password(signupRequest.getPassword())
                .nickName(signupRequest.getNickName())
                .score(0)
                .level(0)
                .role(ROLE_USER)
                .build();
        user.encryptPassword(passwordEncoder);
        this.userRepository.save(user);
    }

    @Override
    public UserDto.LoginResponse login(LoginRequest loginRequest) {
        TokenInfoResponse tokenInfoResponse = this.validateLogin(loginRequest);
        return UserDto.LoginResponse.from(tokenInfoResponse);
    }

    private TokenInfoResponse validateLogin(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword());
        Authentication authentication = this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenInfoResponse tokenInfoResponse = this.tokenProvider.createToken(authentication);

        return tokenInfoResponse;
    }

    @Override
    public User validateUserId(String userId) {
        return this.userRepository.findByUserId(userId).orElseThrow(NotFoundUserIdException::new);
    }


    private void validateOverlap(String userId) {
        userRepository.findByUserId(userId)
                .ifPresent((m -> {
                    throw new OverlapUserException();
                }));
    }

}

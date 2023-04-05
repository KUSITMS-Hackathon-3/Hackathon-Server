package com.example.hackathon.domain.user.service;

import com.example.hackathon.domain.user.dto.UserDto.LoginResponse;
import com.example.hackathon.domain.user.dto.UserDto.LogoutRequest;
import com.example.hackathon.global.config.security.jwt.JwtTokenProvider;
import com.example.hackathon.global.config.security.redis.RedisRepository;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.NoSuchElementException;

import static com.example.hackathon.domain.user.constant.UserConstants.Role.ROLE_USER;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider tokenProvider;
    private final RedisRepository redisRepository;


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
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            TokenInfoResponse tokenInfoResponse = this.validateLogin(loginRequest);
            return LoginResponse.from(tokenInfoResponse);
        } catch (AuthenticationException e) {
            throw e;
        }
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

    @Override
    public LoginResponse reIssueToken(Long userIdx) {
        try {
            String refreshToken = redisRepository.getValues(userIdx.toString()).orElseThrow();
            Authentication authentication = tokenProvider.getAuthentication(refreshToken);
            TokenInfoResponse token = tokenProvider.createToken(authentication);

            log.info("재발급 & 저장!");

            return LoginResponse.from(token);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("RefreshToken이 올바르지 않습니다.");
        }
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        String token = logoutRequest.getToken().substring(7);
        /**
         * Bearer가 함께 넘어오는가
         * 일단은 넘어오는것으로 처리
         */
        Long expiration = tokenProvider.getExpiration(token);
        redisRepository.deleteValues(String.valueOf(logoutRequest.getUserIdx())); //Refresh Token 삭제
        redisRepository.setValues("blackList:" + token, token, Duration.ofSeconds(expiration)); //Access Token 남은 시간동안 블랙리스트
    }

    private void validateOverlap(String userId) {
        userRepository.findByUserId(userId)
                .ifPresent((m -> {
                    throw new OverlapUserException();
                }));
    }

}

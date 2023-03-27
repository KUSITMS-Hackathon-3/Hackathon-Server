package com.example.hackathon.global.config.security.jwt;

import com.example.hackathon.domain.user.dto.UserDto;
import com.example.hackathon.domain.user.entity.User;
import com.example.hackathon.domain.user.repository.UserRepository;
import com.example.hackathon.domain.user.service.UserServiceImpl;
import com.example.hackathon.global.config.security.jwt.JwtConstants.JWTExceptionList;
import com.example.hackathon.global.dto.TokenInfoResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN = "RefreshToken";
    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();
        String refreshToken = resolveRefreshToken(request);
        try {
            if (StringUtils.hasText(refreshToken) && tokenProvider.validateRefreshToken(refreshToken)) {
                Long userIdx = tokenProvider.getUserIdx(refreshToken);
                Authentication authentication = tokenProvider.getAuthentication(userIdx);
                TokenInfoResponse token = tokenProvider.createToken(authentication);
                response.setHeader("accessToken", token.getAccessToken());
                response.setHeader("refreshToken", token.getAccessToken());
            }
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
            }
        } catch (SecurityException | MalformedJwtException e) {
            request.setAttribute("exception", JWTExceptionList.WRONG_TYPE_TOKEN.getErrorCode());
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", JWTExceptionList.EXPIRED_TOKEN.getErrorCode());
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", JWTExceptionList.UNSUPPORTED_TOKEN.getErrorCode());
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", JWTExceptionList.WRONG_TOKEN.getErrorCode());
        } catch (Exception e) {
            log.error("================================================");
            log.error("JwtFilter - doFilterInternal() 오류발생");
            log.error("token : {}", jwt);
            log.error("Exception Message : {}", e.getMessage());
            log.error("Exception StackTrace : ");
            e.printStackTrace();
            log.error("================================================");
            request.setAttribute("exception", JWTExceptionList.UNKNOWN_ERROR.getErrorCode());
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String resolveRefreshToken(HttpServletRequest request) {
        String bearerRefreshToken = request.getHeader(REFRESH_TOKEN);
        if (StringUtils.hasText(bearerRefreshToken) && bearerRefreshToken.startsWith("Bearer ")) {
            return bearerRefreshToken.substring(7);
        }
        return null;
    }
}
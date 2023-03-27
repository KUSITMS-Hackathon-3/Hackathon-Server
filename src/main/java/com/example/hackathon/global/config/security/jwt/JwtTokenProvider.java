package com.example.hackathon.global.config.security.jwt;

import com.example.hackathon.domain.user.dto.UserDto;
import com.example.hackathon.domain.user.service.UserServiceImpl;
import com.example.hackathon.global.config.security.redis.RedisRepository;
import com.example.hackathon.global.dto.TokenInfoResponse;
import com.example.hackathon.domain.user.entity.User;
import com.example.hackathon.domain.user.exception.NotFoundUserIdException;
import com.example.hackathon.domain.user.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {
    private final RedisRepository redisRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_IDX = "userIdx";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-validity-in-seconds}")
    private long accessTokenValidityTime;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityTime;

    private final UserRepository userRepository;

    private Key key;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenInfoResponse createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        Date accessTokenValidity = new Date(now + this.accessTokenValidityTime);
        Date refreshTokenValidity = new Date(now + this.refreshTokenValidityTime);

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        Long userIdx = principal.getUser().getUserIdx();

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(accessTokenValidity)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenValidity)
                .claim(USER_IDX, userIdx)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        /**
         * refreshToken redis에 저장
         */
        updateRefreshToken(userIdx, refreshToken);

        return TokenInfoResponse.from("Bearer", accessToken, refreshToken, refreshTokenValidityTime);
    }

    public void updateRefreshToken(Long userIdx, String refreshToken) {
        try {
            log.info("refreshToken 저장");
            redisRepository.setValues(String.valueOf(userIdx), refreshToken, Duration.ofSeconds(refreshTokenValidityTime));
        } catch (NoSuchElementException e) {
            log.error("일치하는 회원이 없습니다.");
            throw e;
        }
    }

    public Authentication getAuthentication(String accessToken) {

        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        //User user = new User(claims.getSubject(), "", authorities);
        User user = this.userRepository.findByUserId(claims.getSubject()).orElseThrow(NotFoundUserIdException::new);
        return new UsernamePasswordAuthenticationToken(new UserDetailsImpl(user), accessToken, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw e;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw e;
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw e;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Object userIdx = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get(USER_IDX);
            redisRepository.getValues(userIdx.toString()).orElseThrow();
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw e;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw e;
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw e;
        } catch (NoSuchElementException e) {
            log.error("유효하지 않은 JWT 입니다.");
            throw e;
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Long getUserIdx(String refreshToken) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken).getBody();
        Long userIdx = claims.get(USER_IDX, Long.class);
        return userIdx;
    }

    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

    public Authentication getAuthentication(Long userIdx) {
        try {
            User user = userRepository.findById(userIdx).orElseThrow();

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());
            Authentication authentication = this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            return authentication;
        } catch (NoSuchElementException e) {
            log.error("올바르지 않은 userIdx 입니다.");
            throw e;
        }
    }
}



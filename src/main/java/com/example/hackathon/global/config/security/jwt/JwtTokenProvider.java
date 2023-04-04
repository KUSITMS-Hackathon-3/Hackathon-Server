package com.example.hackathon.global.config.security.jwt;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {
    private final RedisRepository redisRepository;
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

        Date current = new Date();

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        Long userIdx = principal.getUser().getUserIdx();

        String accessToken = createAccessToken(authorities, current, userIdx);
        String refreshToken = createRefreshToken(authorities, current, userIdx);

        /**
         * refreshToken redis에 저장하는 중
         */
        updateRefreshToken(userIdx, refreshToken);

        return TokenInfoResponse.from("Bearer", accessToken, refreshToken, accessTokenValidityTime);
    }

    private String createRefreshToken(String authorities, Date current, Long userIdx) {
        Date refreshTokenValidity = new Date(current.getTime() + this.refreshTokenValidityTime);

        String refreshToken = Jwts.builder()
                .setSubject("Refresh")
                .claim(AUTHORITIES_KEY, authorities)
                .claim(USER_IDX, userIdx)
                .setIssuedAt(current)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(refreshTokenValidity)
                .compact();
        return refreshToken;
    }

    private String createAccessToken(String authorities, Date current, Long userIdx) {
        Date accessTokenValidity = new Date(current.getTime() + this.accessTokenValidityTime);

        String accessToken = Jwts.builder()
                .setSubject("Access")
                .claim(AUTHORITIES_KEY, authorities)
                .claim(USER_IDX, userIdx)
                .setIssuedAt(current)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(accessTokenValidity)
                .compact();

        return accessToken;
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
    /**
     * createToken 하면서 RefreshToken은 Redis에 저장하도록 했슴당
     * 만약 맘에 안들면 삭제해줘...
     */

    public Authentication getAuthentication(String token) {

        Claims claims = parseClaims(token);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        String userIdx = String.valueOf(claims.get(USER_IDX));
        User user = this.userRepository.findById(Long.parseLong(userIdx))
                .orElseThrow(NotFoundUserIdException::new);

        return new UsernamePasswordAuthenticationToken(new UserDetailsImpl(user), token, authorities);
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
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
        if (!getSubject(token).equals("Refresh"))
            throw new IllegalArgumentException("Refresh Token이 아닙니다.");
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            String userIdx = getUserIdx(token);
            redisRepository.getValues(userIdx).orElseThrow();
            /**
             * Redis에 userIdx를 Key값으로 저장하려고 해서 Redis에 존재하는지 검사하는 로직이었어
             * 그래서 RefreshToken 생성할 때 claim에 userIdx도 지금은 넣어지고 있을거야
             */
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

    public boolean checkBlackList(String token) {
        if (redisRepository.checkBlackList(token).isPresent())
            throw new IllegalArgumentException("로그아웃된 유저입니다.");
        return true;
    }

    public String getSubject(String token) {
        Claims claims = parseClaims(token);

        return claims.getSubject();
    }

    public String getUserIdx(String token) {
        Claims claims = parseClaims(token);

        return claims.get(USER_IDX).toString();
    }

    public Long getExpiration(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }
}



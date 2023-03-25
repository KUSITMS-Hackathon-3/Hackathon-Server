package com.example.hackathon.global.config.security.util;

import com.example.hackathon.domain.user.entity.User;
import com.example.hackathon.global.config.security.exception.RequiredLoggedinException;
import com.example.hackathon.global.config.security.jwt.UserDetailsImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class SecurityUtils {

    public static User getLoggedInUser() {
        try {
            return ((UserDetailsImpl) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal()).getUser();
        } catch (NullPointerException e) {
            throw new RequiredLoggedinException();
        }
    }

}

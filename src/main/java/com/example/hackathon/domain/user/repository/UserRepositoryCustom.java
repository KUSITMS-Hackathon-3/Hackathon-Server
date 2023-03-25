package com.example.hackathon.domain.user.repository;

import com.example.hackathon.domain.user.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findById(String email);
}

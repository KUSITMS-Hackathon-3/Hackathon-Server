package com.example.hackathon.domain.user.repository;

import com.example.hackathon.domain.user.entity.QUser;
import com.example.hackathon.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.example.hackathon.domain.user.entity.QUser.user;


public class UserRepostioryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public UserRepostioryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(queryFactory.selectFrom(user)
                .where(user.userId.eq(userId))
                .fetchFirst());
    }
}

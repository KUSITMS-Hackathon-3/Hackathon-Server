package com.example.hackathon.domain.recipe.entity;

import com.example.hackathon.global.entity.BaseTimeEntity;
import com.example.hackathon.domain.rcontent.entity.Rcontent;
import com.example.hackathon.domain.user.entity.User;
import com.example.hackathon.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Recipe extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeIdx;

    private String title;

    private int level;

    private String imageUrl;

    private String tag;

    private int likeNum;

    @ManyToOne
    @JoinColumn(name = "userIdx")
    User user;
}

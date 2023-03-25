package com.example.hackathon.domain.recipe.entity;

import com.example.hackathon.domain.rcontent.entity.Rcontent;
import com.example.hackathon.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeIdx;

    private String title;

    private int level;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "userIdx")
    User user;
}

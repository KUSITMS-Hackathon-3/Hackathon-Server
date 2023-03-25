package com.example.hackathon.domain.rcomment.entity;

import com.example.hackathon.domain.board.entity.Board;
import com.example.hackathon.domain.recipe.entity.Recipe;
import com.example.hackathon.domain.user.entity.User;
import com.example.hackathon.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Rcomment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rcomment_idx")
    private Long rcommentIdx;

    private String content;

    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_idx")
    private Recipe recipe;

    /**
     * 연관관계 매핑
     */


    public void deleteComment(){
        this.isDeleted=true;
    }

}

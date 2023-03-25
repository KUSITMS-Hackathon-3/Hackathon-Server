package com.example.hackathon.domain.board.entity;

import com.example.hackathon.domain.comment.entity.Comment;
import com.example.hackathon.global.entity.BaseTimeEntity;
import com.example.hackathon.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardIdx;

    private String title;

    private String content;

    private String imageUrl;

    private Integer likeNums;

    private Integer commentNums;

    private boolean isDeleted;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @OneToMany(mappedBy="board")
    private List<Comment> comments=new ArrayList<>();


    /**
     * 연관관계 매핑
     */

    public void addUser(User user){
        this.user=user;
        user.getBoards().add(this);
    }

    public void deleteBoard(){
        this.isDeleted=true;
    }

    public void likeBoard(){
        this.likeNums+=1;
    }

    public void updateAll(String title, String content) {
    }
}

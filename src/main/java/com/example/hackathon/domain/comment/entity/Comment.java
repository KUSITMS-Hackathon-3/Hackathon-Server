package com.example.hackathon.domain.comment.entity;

import com.example.hackathon.domain.board.entity.Board;
import com.example.hackathon.domain.user.entity.User;
import com.example.hackathon.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder

public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long commentIdx;

    private String content;

    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_idx")
    private Board board;

    /**
     * 연관관계 매핑
     */


    public void addBoard(Board board){
        this.board=board;
        board.getComments().add(this);
    }

    public void addUser(User user){
        this.user=user;
        user.getComments().add(this);
    }

    public void deleteComment(){
        this.isDeleted=true;
    }

}

package com.example.hackathon.domain.user.entity;

import com.example.hackathon.domain.board.entity.Board;
import com.example.hackathon.domain.comment.entity.Comment;
import com.example.hackathon.global.entity.BaseTimeEntity;
import com.example.hackathon.domain.user.constant.UserConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_idx")
    private Long userIdx;

    private String userId;

    private String password;

    private String nickName;

    private boolean isDeleted;

    private Integer score;

    private Integer level;

    @Enumerated(EnumType.STRING)
    private UserConstants.Role role;

    @OneToMany(mappedBy="user")
    private List<Board> boards=new ArrayList<>();

    @OneToMany(mappedBy="user")
    private List<Comment> comments=new ArrayList<>();

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

    public void certificationReward(){
        
        this.score+=25;
        if(score/50>level){
            this.level+=1;
        }
    }

    public void recipeReward(){
        this.score+=50;
        if(score/50>level){
            this.level+=1;
        }
    }
}

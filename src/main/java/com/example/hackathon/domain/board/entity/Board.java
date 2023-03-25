package com.example.hackathon.domain.board.entity;

import com.example.hackathon.domain.user.entity.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardIdx;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "userIdx")
    private User user;
}

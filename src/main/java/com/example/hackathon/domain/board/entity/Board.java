package com.example.hackathon.domain.board.entity;

import com.example.hackathon.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
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

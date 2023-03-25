package com.example.hackathon.domain.certification.entity;

import com.example.hackathon.domain.global.entity.BaseTimeEntity;
import com.example.hackathon.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Certification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificationIdx;

    private String imageUrl;

    private String memo;

    @ManyToOne
    @JoinColumn(name = "userIdx")
    private User user;
}

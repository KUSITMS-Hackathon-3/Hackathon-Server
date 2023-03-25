package com.example.hackathon.domain.dietRecommend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DietRecommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dietRecommendIdx;

    private String dietName;

    private String imageUrl;

    private String description;

    private String advantage;
}

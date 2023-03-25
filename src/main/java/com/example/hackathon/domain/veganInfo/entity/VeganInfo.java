package com.example.hackathon.domain.veganInfo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class VeganInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long veganInfoIdx;

    private String title;

    private String comment;
}

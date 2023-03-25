package com.example.hackathon.domain.recipe.dto;

import com.example.hackathon.domain.rcomment.entity.Rcomment;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CommentDto {
    private String nickname;
    private String content;

    public static CommentDto of(Rcomment rcomment) {
        return CommentDto.builder()
                .content(rcomment.getContent())
                .nickname(rcomment.getContent())
                .build();
    }
}

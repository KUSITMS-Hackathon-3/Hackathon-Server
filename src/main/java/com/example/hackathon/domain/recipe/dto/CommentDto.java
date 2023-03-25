package com.example.hackathon.domain.recipe.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CommentDto {
    private String nickname;
    private String content;
}

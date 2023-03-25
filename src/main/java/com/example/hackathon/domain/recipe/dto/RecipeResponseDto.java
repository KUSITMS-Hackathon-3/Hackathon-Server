package com.example.hackathon.domain.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RecipeResponseDto {
    private String title;
    private int level;
    private LocalDateTime createdDate;
    private String imageUrl;
    private Long userIdx;
    private String tag;
    private int likeNum;
    private List<RcontentDto> rcontentDtos;
    private List<CommentDto> commentDtos;
}

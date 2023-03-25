package com.example.hackathon.domain.recipe.dto;

import com.example.hackathon.domain.rcontent.entity.Rcontent;
import com.example.hackathon.domain.user.entity.User;
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
    private String nickname;

    private List<Rcontent> rContents;
}

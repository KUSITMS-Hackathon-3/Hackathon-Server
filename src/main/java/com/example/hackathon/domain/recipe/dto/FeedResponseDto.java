package com.example.hackathon.domain.recipe.dto;

import com.example.hackathon.domain.ringredient.entity.Ringredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedResponseDto {
    private Long recipeIdx;
    private String title;
    private int level;
    private String tag;
    private String imageUrl;

//    private List<Ringredient> ringredients;
}

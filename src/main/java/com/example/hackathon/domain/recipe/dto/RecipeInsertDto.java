package com.example.hackathon.domain.recipe.dto;

import com.example.hackathon.domain.rcontent.entity.Rcontent;
import com.example.hackathon.domain.ringredient.entity.Ringredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class RecipeInsertDto {
    private List<Rcontent> rcontents;
    private List<Ringredient> ringredients;
    private String title;
    private int level;
    private Long userIdx;
    private String imageUrl;
    private String tag;
}

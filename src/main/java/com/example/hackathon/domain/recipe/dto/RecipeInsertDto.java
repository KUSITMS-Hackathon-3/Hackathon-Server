package com.example.hackathon.domain.recipe.dto;

import com.example.hackathon.domain.rcontent.entity.Rcontent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RecipeInsertDto {
    private List<Rcontent> rcontents = new ArrayList<>();
    private String title;
    private int level;
    private Long userIdx;
    private String imageUrl;
}

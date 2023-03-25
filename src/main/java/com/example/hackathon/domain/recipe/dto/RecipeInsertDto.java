package com.example.hackathon.domain.recipe.dto;

import com.example.hackathon.domain.rcontent.entity.Rcontent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Setter
public class RecipeInsertDto {
    private List<Rcontent> rcontents;
    private String title;
    private int level;
    private Long userIdx;
    private String imageUrl;
}

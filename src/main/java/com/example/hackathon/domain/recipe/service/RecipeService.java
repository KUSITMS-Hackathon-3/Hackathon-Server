package com.example.hackathon.domain.recipe.service;

import com.example.hackathon.domain.recipe.dto.FeedResponseDto;
import com.example.hackathon.domain.recipe.dto.RecipeInsertDto;
import com.example.hackathon.domain.recipe.dto.RecipeResponseDto;

import java.util.List;


public interface RecipeService {
    void save(RecipeInsertDto recipeInsertDto);
    List<FeedResponseDto> findAllFeeds();
    RecipeResponseDto findRecipe(Long recipeIdx);
}

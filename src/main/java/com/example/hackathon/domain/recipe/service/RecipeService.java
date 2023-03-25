package com.example.hackathon.domain.recipe.service;

import com.example.hackathon.domain.rcontent.entity.Rcontent;
import com.example.hackathon.domain.rcontent.repository.RcontentRepository;
import com.example.hackathon.domain.recipe.dto.FeedResponseDto;
import com.example.hackathon.domain.recipe.dto.RecipeInsertDto;
import com.example.hackathon.domain.recipe.dto.RecipeResponseDto;
import com.example.hackathon.domain.recipe.entity.Recipe;
import com.example.hackathon.domain.recipe.repository.RecipeRepository;
import com.example.hackathon.domain.ringredient.entity.Ringredient;
import com.example.hackathon.domain.ringredient.repository.RingredientRepository;
import com.example.hackathon.domain.user.entity.User;
import com.example.hackathon.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RcontentRepository rcontentRepository;
    private final UserRepository userRepository;
    private final RingredientRepository ringredientRepository;

    public void save(RecipeInsertDto recipeInsertDto) throws Exception {
        Optional<User> optionalUser = userRepository.findById(recipeInsertDto.getUserIdx());
        if (optionalUser.isEmpty())
            throw new Exception();

        User user = optionalUser.get();
        Recipe recipe = Recipe.builder()
                .imageUrl(recipeInsertDto.getImageUrl())
                .level(recipeInsertDto.getLevel())
                .title(recipeInsertDto.getTitle())
                .user(user)
                .build();

        recipeRepository.save(recipe);

        for (Rcontent rcontent: recipeInsertDto.getRcontents()) {
            rcontent.addRecipe(recipe);
            rcontentRepository.save(rcontent);
        }

        for (Ringredient ringredient : recipeInsertDto.getRingredients()) {
            ringredient.addRecipe(recipe);
            ringredientRepository.save(ringredient);
        }
    }

    public List<FeedResponseDto> findAllFeeds() {
        List<Recipe> allFeeds = recipeRepository.findAllByOrderByCreatedAtDesc();

        List<FeedResponseDto> feedResponseDtos = new ArrayList<>();
        for (Recipe recipe : allFeeds) {
            FeedResponseDto feedResponseDto = FeedResponseDto.builder()
                    .tag(recipe.getTag())
                    .recipeIdx(recipe.getRecipeIdx())
                    .imageUrl(recipe.getImageUrl())
                    .level(recipe.getLevel())
                    .title(recipe.getTitle())
                    .build();

            feedResponseDtos.add(feedResponseDto);
        }

        return feedResponseDtos;
    }

    public RecipeResponseDto findRecipe(Long recipeIdx) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeIdx);
        Recipe recipe = optionalRecipe.get();

        List<Rcontent> rContents = rcontentRepository.findAllByRecipe_RecipeIdx(recipeIdx);

        RecipeResponseDto recipeResponseDto = RecipeResponseDto.builder()
                .createdDate(recipe.getCreatedAt())
                .imageUrl(recipe.getImageUrl())
                .level(recipe.getLevel())
                .title(recipe.getTitle())
                .rContents(rContents)
                .nickname(recipe.getUser().getNickName())
                .userIdx(recipe.getUser().getUserIdx())
                .build();

        return recipeResponseDto;
    }
}

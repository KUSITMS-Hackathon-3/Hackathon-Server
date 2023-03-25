package com.example.hackathon.domain.recipe.service;

import com.example.hackathon.domain.rcomment.entity.Rcomment;
import com.example.hackathon.domain.rcomment.repository.RcommentRepository;
import com.example.hackathon.domain.rcontent.entity.Rcontent;
import com.example.hackathon.domain.rcontent.repository.RcontentRepository;
import com.example.hackathon.domain.recipe.dto.*;
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
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RcontentRepository rcontentRepository;
    private final UserRepository userRepository;
    private final RingredientRepository ringredientRepository;
    private final RcommentRepository rcommentRepository;

    @Override
    public void save(RecipeInsertDto recipeInsertDto) {
        User user = userRepository.findById(recipeInsertDto.getUserIdx())
                .orElseThrow();

        Recipe recipe = Recipe.builder()
                .imageUrl(recipeInsertDto.getImageUrl())
                .level(recipeInsertDto.getLevel())
                .title(recipeInsertDto.getTitle())
                .tag(recipeInsertDto.getTag())
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

        user.recipeReward();
        this.userRepository.save(user);
    }

    @Override
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

    @Override
    public RecipeResponseDto findRecipe(Long recipeIdx) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeIdx);
        Recipe recipe = optionalRecipe.get();

        List<Rcontent> rContents = rcontentRepository.findAllByRecipe_RecipeIdx(recipeIdx);
        List<RcontentDto> rcontentDtos = new ArrayList<>();

        List<Rcomment> rcomments = rcommentRepository.findAllByRecipe_RecipeIdx(recipeIdx);
        List<CommentDto> commentDtos = new ArrayList<>();

        for (Rcontent rcontent : rContents) {
            RcontentDto rcontentDto = RcontentDto.of(rcontent);
            rcontentDtos.add(rcontentDto);
        }

        for (Rcomment rcomment : rcomments) {
            CommentDto commentDto = CommentDto.of(rcomment);
            commentDtos.add(commentDto);
        }


        RecipeResponseDto recipeResponseDto = RecipeResponseDto.builder()
                .createdDate(recipe.getCreatedAt())
                .imageUrl(recipe.getImageUrl())
                .level(recipe.getLevel())
                .title(recipe.getTitle())
                .rcontentDtos(rcontentDtos)
                .likeNum(recipe.getLikeNum())
                .userIdx(recipe.getUser().getUserIdx())
                .commentDtos(commentDtos)
                .tag(recipe.getTag())
                .build();

        return recipeResponseDto;
    }
}

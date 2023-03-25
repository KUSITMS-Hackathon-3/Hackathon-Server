package com.example.hackathon.domain.recipe.service;

import com.example.hackathon.domain.rcontent.entity.Rcontent;
import com.example.hackathon.domain.rcontent.repository.RcontentRepository;
import com.example.hackathon.domain.recipe.dto.RecipeInsertDto;
import com.example.hackathon.domain.recipe.entity.Recipe;
import com.example.hackathon.domain.recipe.repository.RecipeRepository;
import com.example.hackathon.domain.user.entity.User;
import com.example.hackathon.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RcontentRepository rcontentRepository;
    private final UserRepository userRepository;

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
    }

    public void findAllFeeds() {
        List<Recipe> allRecipes = recipeRepository.findAll();
    }
}

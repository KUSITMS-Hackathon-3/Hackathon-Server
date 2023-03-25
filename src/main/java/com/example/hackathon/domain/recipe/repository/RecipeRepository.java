package com.example.hackathon.domain.recipe.repository;

import com.example.hackathon.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}

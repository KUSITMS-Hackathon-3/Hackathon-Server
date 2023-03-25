package com.example.hackathon.domain.rcontent.repository;

import com.example.hackathon.domain.rcontent.entity.Rcontent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RcontentRepository extends JpaRepository<Rcontent, Long> {
    List<Rcontent> findAllByRecipe_RecipeIdx(Long recipeIdx);
}

package com.example.hackathon.domain.rcomment.repository;

import com.example.hackathon.domain.rcomment.entity.Rcomment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RcommentRepository extends JpaRepository<Rcomment, Long> {
    List<Rcomment> findAllByRecipe_RecipeIdx(Long recipeIdx);
}

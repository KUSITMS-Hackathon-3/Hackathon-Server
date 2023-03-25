package com.example.hackathon.domain.ringredient.entity;

import com.example.hackathon.domain.recipe.entity.Recipe;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Ringredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientIdx;

    private String element;

    @ManyToOne
    @JoinColumn(name = "recipeIdx")
    private Recipe recipe;

    public void addRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}

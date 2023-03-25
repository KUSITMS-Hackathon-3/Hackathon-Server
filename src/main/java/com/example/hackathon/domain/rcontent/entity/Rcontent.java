package com.example.hackathon.domain.rcontent.entity;

import com.example.hackathon.domain.recipe.entity.Recipe;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Rcontent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeContentIdx;

    private String element;

    private int orderIdx;

    @ManyToOne
    @JoinColumn(name = "recipeIdx")
    private Recipe recipe;

    public void addRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}

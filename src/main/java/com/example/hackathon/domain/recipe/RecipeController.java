package com.example.hackathon.domain.recipe;

import com.example.hackathon.domain.global.dto.ResponseDto;
import com.example.hackathon.domain.recipe.constant.RecipeConstant.EBoardResponseMessage;
import com.example.hackathon.domain.recipe.dto.RecipeInsertDto;
import com.example.hackathon.domain.recipe.dto.RecipeResponseDto;
import com.example.hackathon.domain.recipe.entity.Recipe;
import com.example.hackathon.domain.recipe.service.RecipeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe")
@Api(tags = "Recipe API")
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> save(@RequestBody RecipeInsertDto recipeInsertDto) throws Exception {
        recipeService.save(recipeInsertDto);
        ResponseDto<Object> responseDto = ResponseDto.create(EBoardResponseMessage.SAVE_SUCCESS.getMessage());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/findAllFeeds")
    public ResponseEntity<ResponseDto<List<Recipe>>> findAllFeeds() {
        List<Recipe> allFeeds = recipeService.findAllFeeds();
        ResponseDto<List<Recipe>> responseDto = ResponseDto.create(EBoardResponseMessage.SELECT_ALL_SUCCESS.getMessage(), allFeeds);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/findRecipe/{recipeIdx}")
    public ResponseEntity<ResponseDto<RecipeResponseDto>> findRecipe(@PathVariable Long recipeIdx) {
        RecipeResponseDto recipe = recipeService.findRecipe(recipeIdx);
        ResponseDto<RecipeResponseDto> responseDto = ResponseDto.create(EBoardResponseMessage.SELECT_SUCCESS.getMessage(), recipe);
        return ResponseEntity.ok(responseDto);
    }
}

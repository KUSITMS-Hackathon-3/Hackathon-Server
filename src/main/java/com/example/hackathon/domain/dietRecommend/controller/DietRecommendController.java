package com.example.hackathon.domain.dietRecommend.controller;

import com.example.hackathon.domain.dietRecommend.constant.DietRecommendConstant;
import com.example.hackathon.domain.dietRecommend.entity.DietRecommend;
import com.example.hackathon.domain.dietRecommend.service.DietService;
import com.example.hackathon.global.dto.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.hackathon.domain.dietRecommend.constant.DietRecommendConstant.EBoardResponseMessage.SELECT_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dietRecommend")
@Api(tags = "DietRecommend API")
public class DietRecommendController {

    private final DietService dietService;

    @ApiOperation(value = "식단추천", notes = "식단추천을 가져옵니다.")
    @GetMapping
    public ResponseEntity<ResponseDto<DietRecommend>> getDietRecommend(){
        return ResponseEntity.ok(ResponseDto.create(SELECT_SUCCESS.getMessage(),this.dietService.getDiet()));
    }
}

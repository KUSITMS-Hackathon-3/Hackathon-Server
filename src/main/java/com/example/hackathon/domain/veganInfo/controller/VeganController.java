package com.example.hackathon.domain.veganInfo.controller;

import com.example.hackathon.domain.recipe.constant.RecipeConstant;
import com.example.hackathon.domain.veganInfo.constant.VeganConstant;
import com.example.hackathon.domain.veganInfo.dto.VeganInfoDto;
import com.example.hackathon.domain.veganInfo.service.VeganInfoService;
import com.example.hackathon.global.dto.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.hackathon.domain.veganInfo.constant.VeganConstant.EBoardResponseMessage.SELECT_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/veganInfo")
@Api(tags = "Vegan API")
public class VeganController {

    private final VeganInfoService veganInfoService;

    @ApiOperation(value = "채식정보", notes = "채식정보를 가져옵니다.")
    @GetMapping
    public ResponseEntity<ResponseDto<VeganInfoDto>> getInfo() {
        VeganInfoDto knowledge = veganInfoService.getKnowledge();
        ResponseDto<VeganInfoDto> responseDto = ResponseDto.create(SELECT_SUCCESS.getMessage(), knowledge);
        return ResponseEntity.ok(responseDto);
    }
}

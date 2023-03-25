package com.example.hackathon.domain.certification.controller;

import com.example.hackathon.domain.certification.constant.CertificationConstant;
import com.example.hackathon.domain.certification.dto.CertificationDateDto;
import com.example.hackathon.domain.certification.dto.CertificationInsertDto;
import com.example.hackathon.domain.certification.service.CertificationService;
import com.example.hackathon.domain.recipe.constant.RecipeConstant;
import com.example.hackathon.global.dto.ResponseDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.hackathon.domain.recipe.constant.RecipeConstant.EBoardResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/certification")
@Api(tags = "Certification API")
public class CertificationController {
    private final CertificationService certificationService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> save(CertificationInsertDto certificationInsertDto) throws Exception {
        certificationService.save(certificationInsertDto);
        ResponseDto<Object> responseDto = ResponseDto.create(SAVE_SUCCESS.getMessage());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/findDate")
    public ResponseEntity<ResponseDto<CertificationDateDto>> findDateCertification(String startDay) {
        CertificationDateDto certificationDateDto = certificationService.findDateCertification(startDay);
        ResponseDto<CertificationDateDto> responseDto = ResponseDto.create(SELECT_SUCCESS.getMessage(), certificationDateDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/findMonth")
    public ResponseEntity<ResponseDto<List<String>>> findAllCertification(String month) {
        List<String> allCertification = certificationService.findAllCertification(month);
        ResponseDto<List<String>> responseDto = ResponseDto.create(SELECT_ALL_SUCCESS.getMessage(), allCertification);
        return ResponseEntity.ok(responseDto);
    }
}

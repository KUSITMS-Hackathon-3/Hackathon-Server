package com.example.hackathon.domain.certification.controller;

import com.example.hackathon.domain.certification.constant.CertificationConstant;
import com.example.hackathon.domain.certification.dto.CertificationDateDto;
import com.example.hackathon.domain.certification.dto.CertificationInsertDto;
import com.example.hackathon.domain.certification.dto.CertificationMonthDto;
import com.example.hackathon.domain.certification.service.CertificationService;
import com.example.hackathon.domain.recipe.constant.RecipeConstant;
import com.example.hackathon.global.dto.ResponseDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.hackathon.domain.recipe.constant.RecipeConstant.EBoardResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/certification")
@Api(tags = "Certification API")
public class CertificationController {
    private final CertificationService certificationService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> save(@RequestBody CertificationInsertDto certificationInsertDto) throws Exception {
        certificationService.save(certificationInsertDto);
        ResponseDto<Object> responseDto = ResponseDto.create(SAVE_SUCCESS.getMessage());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/findDate")
    public ResponseEntity<ResponseDto<CertificationDateDto>> findDateCertification(@RequestBody CertificationDateDto certificationDateDto) {
        CertificationDateDto certification = certificationService.findDateCertification(certificationDateDto);
        ResponseDto<CertificationDateDto> responseDto = ResponseDto.create(SELECT_SUCCESS.getMessage(), certification);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/findMonth")
    public ResponseEntity<ResponseDto<List<String>>> findAllCertification(@RequestBody CertificationMonthDto certificationMonthDto) {
        List<String> allCertification = certificationService.findAllCertification(certificationMonthDto);
        ResponseDto<List<String>> responseDto = ResponseDto.create(SELECT_ALL_SUCCESS.getMessage(), allCertification);
        return ResponseEntity.ok(responseDto);
    }
}

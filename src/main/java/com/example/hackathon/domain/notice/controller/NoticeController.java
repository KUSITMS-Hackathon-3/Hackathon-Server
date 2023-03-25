package com.example.hackathon.domain.notice.controller;

import com.example.hackathon.domain.notice.entity.Notice;
import com.example.hackathon.domain.notice.service.NoticeService;
import com.example.hackathon.global.dto.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.hackathon.domain.veganInfo.constant.VeganConstant.EBoardResponseMessage.SELECT_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
@Api(tags = "Notice API")
public class NoticeController {

    private final NoticeService noticeService;

    @ApiOperation(value = "채식정보", notes = "채식정보를 가져옵니다.")
    @GetMapping
    public ResponseEntity<ResponseDto<List<Notice>>> getInfo() {
        return ResponseEntity.ok(ResponseDto.create(SELECT_SUCCESS.getMessage(),this.noticeService.getNotice()));
    }
}

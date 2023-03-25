package com.example.hackathon.domain.board.controller;

import com.example.hackathon.domain.board.constant.BoardConstants.EBoardController;
import com.example.hackathon.domain.board.dto.BoardDto.CreateRequest;
import com.example.hackathon.domain.board.dto.BoardDto.CreateResponse;
import com.example.hackathon.domain.board.service.BoardService;
import com.example.hackathon.global.dto.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.example.hackathon.domain.board.constant.BoardConstants.EBoardResponseMessage.CREATE_BOARD_SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("board")
@Api(tags = "Board API")
public class BoardController {

    private final BoardService boardService;

    @ApiOperation(value="게시글 작성", notes="게시글을 생성합니다.")
    @PostMapping
    public ResponseEntity<ResponseDto<CreateResponse>> createMemo(@Valid @RequestBody CreateRequest createRequest){
        CreateResponse createResponse=this.boardService.createBoard(createRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(EBoardController.LOCATION_ID_PATH.getValue())
                .buildAndExpand(createResponse.getBoardIdx())
                .toUri();

        return ResponseEntity.created(location).body(ResponseDto.create(CREATE_BOARD_SUCCESS.getMessage(), createResponse));
    }

    //삭제
    //게시글 좋아요
    //댓글 생성
    //게시글 전체 조회
    //게시글 단건 조회 댓글 까지
}

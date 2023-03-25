package com.example.hackathon.domain.board.controller;

import com.example.hackathon.domain.board.constant.BoardConstants.EBoardController;
import com.example.hackathon.domain.board.dto.BoardDto;
import com.example.hackathon.domain.board.dto.BoardDto.CreateRequest;
import com.example.hackathon.domain.board.dto.BoardDto.CreateResponse;
import com.example.hackathon.domain.board.dto.BoardDto.GetAllResponse;
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
import java.util.List;

import static com.example.hackathon.domain.board.constant.BoardConstants.EBoardResponseMessage.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("board")
@Api(tags = "Board API")
public class BoardController {

    private final BoardService boardService;

    @ApiOperation(value="게시글 작성", notes="게시글을 생성합니다.")
    @PostMapping
    public ResponseEntity<ResponseDto<CreateResponse>> createBoard(@Valid @RequestBody CreateRequest createRequest){
        CreateResponse createResponse=this.boardService.createBoard(createRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(EBoardController.LOCATION_ID_PATH.getValue())
                .buildAndExpand(createResponse.getBoardIdx())
                .toUri();

        return ResponseEntity.created(location).body(ResponseDto.create(CREATE_BOARD_SUCCESS.getMessage(), createResponse));
    }

    @ApiOperation(value="게시글 삭제", notes="게시글을 삭제합니다")
    @DeleteMapping("/{boardIdx}")
    public ResponseEntity<ResponseDto> deleteBoard(@PathVariable Long boardIdx){
        this.boardService.deleteBoard(boardIdx);
        return ResponseEntity.ok(ResponseDto.create(DELETE_BOARD_SUCCESS.getMessage()));
    }

    @ApiOperation(value="좋아요 누르기", notes="좋아요를 누릅니다")
    @PostMapping("/like/{boardIdx}")
    public ResponseEntity<ResponseDto> likeBoard(@PathVariable Long boardIdx){
        this.boardService.likeBoard(boardIdx);
        return ResponseEntity.ok(ResponseDto.create(LIKE_BOARD_SUCCESS.getMessage()));
    }

    @ApiOperation(value="게시글을 시간 순으로 조회", notes="게시글을 시간 순으로 전체 조회합니다")
    @GetMapping
    public ResponseEntity<ResponseDto<List<GetAllResponse>>> getAllBoard(){
        return ResponseEntity.ok(ResponseDto.create(GET_ALL_BOARD_SUCCESS.getMessage(),this.boardService.findAllByCreatedDate()));
    }
    
}

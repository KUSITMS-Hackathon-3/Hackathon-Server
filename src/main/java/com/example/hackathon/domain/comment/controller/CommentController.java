package com.example.hackathon.domain.comment.controller;

import com.example.hackathon.domain.comment.constant.CommentConstants.ECommentResponseMessage;
import com.example.hackathon.domain.comment.dto.CommentDto;
import com.example.hackathon.domain.comment.dto.CommentDto.CreateRequest;
import com.example.hackathon.domain.comment.service.CommentService;
import com.example.hackathon.global.dto.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
@Api(tags = "Comment API")
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성합니다.")
    @PostMapping
    public ResponseEntity<ResponseDto<CommentDto.CreateResponse>> createComment(@Valid @RequestBody CreateRequest createRequest){
        return ResponseEntity.ok(ResponseDto.create(ECommentResponseMessage.CREATE_COMMENT_SUCCESS.getMessage(), this.commentService.createComment(createRequest)));
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteComment(@Valid @RequestBody CommentDto.DeleteRequest deleteRequest){
        this.commentService.deleteComment(deleteRequest);
        return ResponseEntity.ok(ResponseDto.create(ECommentResponseMessage.DELETE_COMMENT_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "게시글의 댓글 목록 조회", notes = "게시글의 댓글 목록을 조회합니다.")
    @GetMapping("/{boardIdx}")
    public ResponseEntity<ResponseDto<List<CommentDto.GetResponse>>> getAllComments(@PathVariable Long boardIdx){
        return ResponseEntity.ok(ResponseDto.create(ECommentResponseMessage.GET_ALL_DETAIL_COMMENTS_SUCCESS.getMessage(),
                this.commentService.getAllCommentsByBoardId(boardIdx)));
    }
}

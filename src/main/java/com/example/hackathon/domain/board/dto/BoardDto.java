package com.example.hackathon.domain.board.dto;

import com.example.hackathon.domain.board.entity.Board;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public abstract class BoardDto {

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @ApiModel(description = "게시글 생성을 위한 요청 객체")
    public static class CreateRequest {
        @NotBlank(message = "게시글 제목을 입력해 주세요.")
        @ApiModelProperty(notes = "게시글 제목을 입력해 주세요.")
        private String title;

        @NotBlank(message = "게시글 내용을 입력해 주세요.")
        @ApiModelProperty(notes = "게시글 내용을 입력해 주세요.")
        private String content;

        @NotBlank(message = "게시글 이미지 url을 입력해 주세요.")
        @ApiModelProperty(notes = "게시글 이미지 url을 입력해 주세요.")
        private String imageUrl;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @Builder
    @ApiModel(description = "게시글 생성을 위한 응답객체")
    public static class CreateResponse {
        private Long boardIdx;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String userNickName;
        private String imageUrl;

        public static CreateResponse from(Board board){
            return CreateResponse.builder()
                    .boardIdx(board.getBoardIdx())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdAt(board.getCreatedAt())
                    .updatedAt(board.getUpdatedAt())
                    .userNickName(board.getUser().getNickName())
                    .imageUrl(board.getImageUrl())
                    .build();
        }
    }

    @Getter
    @Builder
    @ApiModel(description = "게시판 전체 조회를 위한 응답 객체")
    public static class GetAllResponse {
        private Long boardIdx;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private String userNickName;
        private String imageUrl;
        private Integer likeNums;
        private Integer commentNums;

        @QueryProjection
        public GetAllResponse(Long boardIdx, String title, String content, LocalDateTime createdAt, String userNickName, String imageUrl, Integer likeNums, Integer commentNums){
            this.boardIdx=boardIdx;
            this.title=title;
            this.content=content;
            this.createdAt=createdAt;
            this.userNickName=userNickName;
            this.imageUrl=imageUrl;
            this.likeNums=likeNums;
            this.commentNums=commentNums;
        }
    }

}

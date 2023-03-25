package com.example.hackathon.domain.comment.dto;

import com.example.hackathon.domain.comment.entity.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public abstract class CommentDto {

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @ApiModel(description = "댓글 작성을 위한 요청 객체")
    public static class CreateRequest {

        @NotNull(message = "게시글 id를 입력해 주세요.")
        @ApiModelProperty(notes = "게시글 idx를 입력해 주세요.")
        private Long boardIdx;

        @NotBlank(message = "댓글 내용을 입력해 주세요.")
        @ApiModelProperty(notes = "댓글 내용을 입력해 주세요.")
        private String content;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @ApiModel(description = "댓글 작성을 위한 응답 객체")
    public static class CreateResponse {
        private Long commentIdx;

        public static CreateResponse from(Comment comment){
            return CreateResponse.builder()
                    .commentIdx(comment.getCommentIdx()).build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ApiModel(description = "댓글 삭제를 위한 요청 객체")
    public static class DeleteRequest {
        @ApiModelProperty(notes = "댓글 id를 입력해주세요.")
        private Long commentIdx;
    }

    @Getter
    @Builder
    @ApiModel(description = "댓글 조회를 위한 응답 객체")
    public static class  GetResponse {
        private Long commentIdx;
        private String content;
        private Long userIdx;
        private String userNickName;
        private LocalDateTime createdAt;

        public GetResponse(Long commentIdx, String content, Long userIdx, String userNickName, LocalDateTime createdAt) {
            this.commentIdx = commentIdx;
            this.content = content;
            this.userIdx = userIdx;
            this.userNickName = userNickName;
            this.createdAt = createdAt;
        }

        public static GetResponse convertCommentToDto(Comment comment) {

            return comment.isDeleted() == true ?
                    new GetResponse(comment.getCommentIdx(), "삭제된 댓글입니다.", null, null, null) :
                    new GetResponse(comment.getCommentIdx(), comment.getContent(), comment.getUser().getUserIdx(), comment.getUser().getNickName(), comment.getCreatedAt());
        }
    }
}

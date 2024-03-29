package com.example.hackathon.domain.comment.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class CommentConstants {

    @Getter
    @RequiredArgsConstructor
    public enum ECommentResponseMessage{
        CREATE_COMMENT_SUCCESS("댓글을 작성했습니다."),
        DELETE_COMMENT_SUCCESS("댓글을 삭제했습니다."),
        GET_ALL_DETAIL_COMMENTS_SUCCESS("게시글의 모든 댓글을 조회했습니다.");
        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    public enum CommentExceptionList {
        NOT_FOUND_COMMENT("C0001", HttpStatus.NOT_FOUND, "해당 commentId로 Comment를 찾을 수 없습니다.");

        private final String errorCode;
        private final HttpStatus httpStatus;
        private final String message;
    }
}

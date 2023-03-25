package com.example.hackathon.domain.comment.repository;

import com.example.hackathon.domain.comment.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {

    Optional<Comment> findNotDeletedByCommentId(Long commentId);
    List<Comment> findAllCommentsByBoardId(Long boardId);

}

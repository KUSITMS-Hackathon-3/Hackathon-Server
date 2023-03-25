package com.example.hackathon.domain.comment.service;

import com.example.hackathon.domain.comment.dto.CommentDto;
import com.example.hackathon.domain.comment.dto.CommentDto.CreateRequest;
import com.example.hackathon.domain.comment.dto.CommentDto.CreateResponse;
import com.example.hackathon.domain.comment.dto.CommentDto.DeleteRequest;
import com.example.hackathon.domain.comment.dto.CommentDto.GetResponse;
import com.example.hackathon.domain.comment.entity.Comment;

import java.util.List;

public interface CommentService {
    CreateResponse createComment(CreateRequest createRequest);
    Comment deleteComment(DeleteRequest deleteRequest);
    List<GetResponse> getAllCommentsByMemoId(Long memoId);
    Comment validateCommentId(Long commentId);
}

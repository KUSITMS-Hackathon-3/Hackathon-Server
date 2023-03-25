package com.example.hackathon.domain.comment.service;

import com.example.hackathon.domain.board.exception.NotFoundBoardException;
import com.example.hackathon.domain.board.repository.BoardRepository;
import com.example.hackathon.domain.comment.dto.CommentDto;
import com.example.hackathon.domain.comment.dto.CommentDto.CreateRequest;
import com.example.hackathon.domain.comment.dto.CommentDto.CreateResponse;
import com.example.hackathon.domain.comment.dto.CommentDto.GetResponse;
import com.example.hackathon.domain.comment.entity.Comment;
import com.example.hackathon.domain.comment.repository.CommentRepository;
import com.example.hackathon.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.hackathon.domain.comment.dto.CommentDto.GetResponse.convertCommentToDto;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommentServiceImpl implements CommentService{

    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    @Override
    public CreateResponse createComment(CreateRequest createRequest) {
        Comment comment = Comment.builder()
                            .content(createRequest.getContent())
                            .user(SecurityUtils.getLoggedInUser())
                            .board(this.boardRepository.findNotDeletedByBoardId(createRequest.getBoardIdx()).orElseThrow(NotFoundBoardException::new))
                            .build();

        this.commentRepository.save(comment);
        return CreateResponse.from(comment);
    }

    @Override
    public Comment deleteComment(CommentDto.DeleteRequest deleteRequest) {
        Comment comment = this.validateCommentId(deleteRequest.getCommentIdx());
        comment.deleteComment();
        return comment;
    }

    @Override
    public List<GetResponse> getAllCommentsByBoardId(Long boardId) {
        List<Comment> allCommentsByBoardId = this.commentRepository.findAllCommentsByBoardId(boardId);
        List<GetResponse> result = new ArrayList<>();
        Map<Long, GetResponse> map = new HashMap<>();
        allCommentsByBoardId.stream().forEach(c -> {
            GetResponse getResponse = convertCommentToDto(c);
            map.put(getResponse.getCommentIdx(), getResponse);
            result.add(getResponse);
        });
        return result;
    }

    @Override
    public Comment validateCommentId(Long commentId) {
        return this.commentRepository.findNotDeletedByCommentId(commentId).orElseThrow();
    }
}

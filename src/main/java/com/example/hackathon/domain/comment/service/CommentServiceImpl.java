package com.example.hackathon.domain.comment.service;

import com.example.hackathon.domain.board.exception.NotFoundCommentException;
import com.example.hackathon.domain.board.service.BoardService;
import com.example.hackathon.domain.comment.dto.CommentDto;
import com.example.hackathon.domain.comment.dto.CommentDto.CreateRequest;
import com.example.hackathon.domain.comment.dto.CommentDto.CreateResponse;
import com.example.hackathon.domain.comment.entity.Comment;
import com.example.hackathon.domain.comment.repository.CommentRepository;
import com.example.hackathon.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

    private final BoardService boardService;

    private final CommentRepository commentRepository;

    @Override
    public CreateResponse createComment(CreateRequest createRequest) {
        Comment comment = Comment.builder()
                            .content(createRequest.getContent())
                            .user(SecurityUtils.getLoggedInUser())
                            .board(this.boardService.validateByBoardId(createRequest.getBoardIdx()))
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
    public List<CommentDto.GetResponse> getAllCommentsByMemoId(Long memoId) {
//        List<Comment> allCommentsByBoardId = this.commentRepository.findAllCommentsByMemoId(memoId);
//        List<GetResponse> result = new ArrayList<>();
//        Map<Long, GetResponse> map = new HashMap<>();
//        allCommentsByBoardId.stream().forEach(c -> {
//            GetResponse getResponse = convertCommentToDto(c);
//            map.put(getResponse.getCommentId(), getResponse);
//            result.add(getResponse);
//        });
//        return result;
        return null;
    }

    @Override
    public Comment validateCommentId(Long commentId) {
        return this.commentRepository.findNotDeletedByCommentId(commentId).orElseThrow(NotFoundCommentException::new);
    }
}

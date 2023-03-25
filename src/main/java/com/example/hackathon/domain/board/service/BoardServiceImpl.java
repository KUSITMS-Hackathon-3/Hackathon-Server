package com.example.hackathon.domain.board.service;

import com.example.hackathon.domain.board.dto.BoardDto.CreateRequest;
import com.example.hackathon.domain.board.dto.BoardDto.CreateResponse;
import com.example.hackathon.domain.board.entity.Board;
import com.example.hackathon.domain.board.exception.NotFoundCommentException;
import com.example.hackathon.domain.board.repository.BoardRepository;
import com.example.hackathon.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public CreateResponse createBoard(CreateRequest createRequest) {
        Board board= Board.builder()
                .title(createRequest.getTitle())
                .content(createRequest.getContent())
                .imageUrl(createRequest.getImageUrl())
                .user(SecurityUtils.getLoggedInUser())
                .commentNums(0)
                .likeNums(0)
                .build();
        this.boardRepository.save(board);
        return CreateResponse.from(board);
    }

    @Override
    public Board validateByBoardId(Long boardId) {
        return this.boardRepository.findNotDeletedByBoardId(boardId).orElseThrow(NotFoundCommentException::new);
    }
}

package com.example.hackathon.domain.board.service;

import com.example.hackathon.domain.board.dto.BoardDto;
import com.example.hackathon.domain.board.dto.BoardDto.CreateRequest;
import com.example.hackathon.domain.board.dto.BoardDto.CreateResponse;
import com.example.hackathon.domain.board.entity.Board;
import com.example.hackathon.domain.board.exception.NotFoundBoardException;
import com.example.hackathon.domain.board.repository.BoardRepository;
import com.example.hackathon.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return this.boardRepository.findNotDeletedByBoardId(boardId).orElseThrow(NotFoundBoardException::new);
    }

    @Override
    public Board deleteBoard(Long boardId) {
        Board board=this.validateByBoardId(boardId);
        board.deleteBoard();
        return board;
    }

    @Override
    public void likeBoard(Long boardId) {
        Board board=this.validateByBoardId(boardId);
        board.likeBoard();
    }

    @Override
    public List<BoardDto.GetAllResponse> findAllByCreatedDate() {
        return this.boardRepository.findAllByCreatedDate();
    }
}

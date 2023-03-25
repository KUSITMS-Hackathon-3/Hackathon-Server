package com.example.hackathon.domain.board.repository;

import com.example.hackathon.domain.board.dto.BoardDto;
import com.example.hackathon.domain.board.dto.BoardDto.GetAllResponse;
import com.example.hackathon.domain.board.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepositoryCustom {
    Optional<Board> findNotDeletedByBoardId(Long boardId);
    List<GetAllResponse> findAllByCreatedDate();
}

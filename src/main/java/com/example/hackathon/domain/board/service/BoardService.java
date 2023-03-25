package com.example.hackathon.domain.board.service;

import com.example.hackathon.domain.board.dto.BoardDto;
import com.example.hackathon.domain.board.dto.BoardDto.CreateRequest;
import com.example.hackathon.domain.board.dto.BoardDto.CreateResponse;
import com.example.hackathon.domain.board.dto.BoardDto.GetAllResponse;
import com.example.hackathon.domain.board.entity.Board;
import com.example.hackathon.domain.board.repository.BoardRepository;
import com.example.hackathon.domain.user.entity.User;
import com.example.hackathon.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    CreateResponse createBoard(CreateRequest createRequest);
    Board validateByBoardId(Long boardId);
    Board deleteBoard(Long boardId);
    void likeBoard(Long boardId);
    List<GetAllResponse> findAllByCreatedDate();
}

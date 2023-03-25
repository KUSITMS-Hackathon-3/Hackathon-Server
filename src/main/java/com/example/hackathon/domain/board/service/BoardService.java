package com.example.hackathon.domain.board.service;

import com.example.hackathon.domain.board.entity.Board;
import com.example.hackathon.domain.board.repository.BoardRepository;
import com.example.hackathon.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public void save(String title, String content, Long userIdx) {
        Board board = Board.builder()
                .content(content)
                .title(title)
                .build();

        boardRepository.save(board);
    }

    public void delete(Long boardIdx) {
        Optional<Board> optionalBoard = boardRepository.findById(boardIdx);

    }
}

package com.example.hackathon.domain.board.service;

import com.example.hackathon.domain.board.entity.Board;
import com.example.hackathon.domain.board.repository.BoardRepository;
import com.example.hackathon.domain.user.entity.User;
import com.example.hackathon.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    public void save(String title, String content, Long userIdx) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userIdx);

        if (optionalUser.isEmpty())
            throw new Exception();

        User user = optionalUser.get();

        Board board = Board.builder()
                .user(user)
                .content(content)
                .title(title)
                .build();

        boardRepository.save(board);
    }

    public void delete(Long boardIdx) throws Exception {
        Optional<Board> optionalBoard = boardRepository.findById(boardIdx);

        if (optionalBoard.isEmpty())
            throw new Exception();

        Board findBoard = optionalBoard.get();

        boardRepository.delete(findBoard);
    }
}

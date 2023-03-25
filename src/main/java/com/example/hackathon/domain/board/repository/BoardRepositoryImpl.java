package com.example.hackathon.domain.board.repository;

import com.example.hackathon.domain.board.entity.Board;
import com.example.hackathon.domain.board.entity.QBoard;
import com.example.hackathon.domain.comment.repository.CommentRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.example.hackathon.domain.board.entity.QBoard.board;

public class BoardRepositoryImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    private final CommentRepository commentRepository;

    public BoardRepositoryImpl(EntityManager em, CommentRepository commentRepository){
        this.jpaQueryFactory=new JPAQueryFactory(em);
        this.commentRepository = commentRepository;
    }

    @Override
    public Optional<Board> findNotDeletedByBoardId(Long boardId){
        return Optional.ofNullable(jpaQueryFactory.selectFrom(board)
                .where(board.boardIdx.eq(boardId),
                        board.isDeleted.eq(false))
                .fetchFirst());
    }
}

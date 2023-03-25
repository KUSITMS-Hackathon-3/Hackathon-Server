package com.example.hackathon.domain.board.repository;

import com.example.hackathon.domain.board.dto.BoardDto;
import com.example.hackathon.domain.board.dto.QBoardDto_GetAllResponse;
import com.example.hackathon.domain.board.entity.Board;
import com.example.hackathon.domain.board.entity.QBoard;
import com.example.hackathon.domain.comment.repository.CommentRepository;
import com.example.hackathon.domain.user.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.example.hackathon.domain.board.entity.QBoard.board;
import static com.example.hackathon.domain.user.entity.QUser.user;

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

    @Override
    public List<BoardDto.GetAllResponse> findAllByCreatedDate() {
        return jpaQueryFactory.select(new QBoardDto_GetAllResponse(board.boardIdx,
                board.title,
                board.content,
                board.createdAt,
                user.nickName,
                board.imageUrl,
                board.likeNums,
                board.commentNums))
                .from(board, user)
                .leftJoin(board.user, user)
                .where(board.isDeleted.eq(false))
                .orderBy(board.createdAt.desc())
                .groupBy(board.boardIdx)
                .fetch();
    }
}

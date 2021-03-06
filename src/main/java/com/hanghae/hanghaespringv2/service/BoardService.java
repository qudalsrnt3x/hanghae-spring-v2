package com.hanghae.hanghaespringv2.service;

import com.hanghae.hanghaespringv2.dto.BoardDTO;
import com.hanghae.hanghaespringv2.dto.ReplyDTO;
import com.hanghae.hanghaespringv2.handler.ex.NotFoundException;
import com.hanghae.hanghaespringv2.model.board.Board;
import com.hanghae.hanghaespringv2.model.board.BoardRepository;
import com.hanghae.hanghaespringv2.model.reply.Reply;
import com.hanghae.hanghaespringv2.model.reply.ReplyRepository;
import com.hanghae.hanghaespringv2.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public Board saveBoard(BoardDTO boardDTO, User user) {

        Board createBoard = Board.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .count(0)
                .user(user)
                .build();

        return boardRepository.save(createBoard);
    }

    @Transactional(readOnly = true)
    public Page<Board> getPosts(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board getPostsById(Long id) {

        return boardRepository.findById(id).orElseThrow(
                () -> new NotFoundException("해당하는 게시글을 찾을 수 없습니다.")
        );
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void saveReply(Long id, ReplyDTO replyDTO, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NotFoundException("해당하는 게시글을 찾을 수 없습니다.")
        );

        Reply reply = Reply.builder()
                .board(board)
                .content(replyDTO.getContent())
                .user(user)
                .build();

        replyRepository.save(reply);

    }

    @Transactional
    public void deleteReply(Long id) {
        replyRepository.deleteById(id);
    }


    @Transactional
    public void updateReply(Long id, ReplyDTO replyDTO) {
        // 영속화 먼저
        Reply replyEntity = replyRepository.findById(id).orElseThrow(
                () -> new NotFoundException("해당 댓글이 존재하지 않습니다.")
        );

        replyEntity.update(replyDTO);

    }
}

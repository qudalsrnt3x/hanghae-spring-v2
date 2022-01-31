package com.hanghae.hanghaespringv2.service;

import com.hanghae.hanghaespringv2.dto.BoardDTO;
import com.hanghae.hanghaespringv2.model.board.Board;
import com.hanghae.hanghaespringv2.model.board.BoardRepository;
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
                () -> new IllegalArgumentException("해당하는 게시글이 없습니다. id = " + id)
        );
    }
}

package com.hanghae.hanghaespringv2.controller.api;

import com.hanghae.hanghaespringv2.config.auth.PrincipalDetails;
import com.hanghae.hanghaespringv2.dto.BoardDTO;
import com.hanghae.hanghaespringv2.dto.ResponseDTO;
import com.hanghae.hanghaespringv2.model.board.Board;
import com.hanghae.hanghaespringv2.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/posts")
    public ResponseEntity<ResponseDTO> createPosts(@RequestBody BoardDTO boardDTO,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Board saveBoard = boardService.saveBoard(boardDTO, principalDetails.getUser());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveBoard.getId())
                .toUri();

        return ResponseEntity.ok(new ResponseDTO(HttpStatus.CREATED.value(), "게시글이 작성되었습니다.", location));
    }

    @GetMapping("/posts")
    public Page<Board> getPosts(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)
                                        Pageable pageable) {

        return boardService.getPosts(pageable);
    }


    @DeleteMapping("/posts/{id}")
    public Long deletePost(@PathVariable Long id) {

        boardService.deletePost(id);
        return id;
    }
}

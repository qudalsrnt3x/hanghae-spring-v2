package com.hanghae.hanghaespringv2.controller.api;

import com.hanghae.hanghaespringv2.config.auth.PrincipalDetails;
import com.hanghae.hanghaespringv2.dto.BoardDTO;
import com.hanghae.hanghaespringv2.dto.CMResponseDTO;
import com.hanghae.hanghaespringv2.dto.ReplyDTO;
import com.hanghae.hanghaespringv2.dto.ResponseDTO;
import com.hanghae.hanghaespringv2.handler.ex.InvalidException;
import com.hanghae.hanghaespringv2.model.board.Board;
import com.hanghae.hanghaespringv2.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;

    @Secured("ROLE_USER")
    @PostMapping("/posts")
    public ResponseEntity<CMResponseDTO<?>> createPosts(@Valid @RequestBody BoardDTO boardDTO,
                                                     @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            throw new InvalidException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());

        Board saveBoard = boardService.saveBoard(boardDTO, principalDetails.getUser());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveBoard.getId())
                .toUri();

        return ResponseEntity.ok(new CMResponseDTO<>(true, HttpStatus.CREATED.value(), "게시글이 저장되었습니다.",
                location));
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

    @PostMapping("/posts/{id}/reply")
    public ResponseDTO saveReply(@PathVariable Long id,
                                 @Valid @RequestBody ReplyDTO replyDTO,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            throw new InvalidException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());

        boardService.saveReply(id, replyDTO, principalDetails.getUser());

        return new ResponseDTO(HttpStatus.CREATED.value(), "댓글 작성이 완료되었습니다.", null);
    }

    @PutMapping("/posts/{postsId}/reply/{id}")
    public ResponseDTO updateReply(@PathVariable Long id,
                                   @RequestBody ReplyDTO replyDTO) {

        boardService.updateReply(id, replyDTO);

        return new ResponseDTO(HttpStatus.OK.value(), "댓글이 수정되었습니다.", null);
    }

    @DeleteMapping("/posts/{postsId}/reply/{id}")
    public Long deleteReply(@PathVariable Long id) {
        boardService.deleteReply(id);
        return id;
    }

}

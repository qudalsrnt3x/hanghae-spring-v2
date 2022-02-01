package com.hanghae.hanghaespringv2.controller;

import com.hanghae.hanghaespringv2.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping({"/", ""})
    public String main() {
        return "index";
    }

    @GetMapping("/posts/{id}")
    public String findBoardById(@PathVariable Long id, Model model) {
        model.addAttribute("posts", boardService.getPostsById(id));

        return "board/detail";
    }
}

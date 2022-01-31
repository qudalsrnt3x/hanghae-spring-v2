package com.hanghae.hanghaespringv2.model.board;

import com.hanghae.hanghaespringv2.model.Timestamped;
import com.hanghae.hanghaespringv2.model.user.User;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}

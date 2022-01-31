package com.hanghae.hanghaespringv2.model.board;

import com.hanghae.hanghaespringv2.model.Timestamped;
import com.hanghae.hanghaespringv2.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    private int count; // TODO 조회수 할 때 사용

    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}

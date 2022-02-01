package com.hanghae.hanghaespringv2.model.reply;

import com.hanghae.hanghaespringv2.dto.ReplyDTO;
import com.hanghae.hanghaespringv2.model.Timestamped;
import com.hanghae.hanghaespringv2.model.board.Board;
import com.hanghae.hanghaespringv2.model.user.User;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reply extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String content;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    public void update(ReplyDTO replyDTO) {
        this.content = replyDTO.getContent();
    }
}

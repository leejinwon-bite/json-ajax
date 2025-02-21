package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.Reply;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public  void testInsert() {

        Long bno = 100L;

        Board board = Board.builder().bno(bno).build();

        for(int i=1;i<=100;i++) {
            Reply reply = Reply.builder()
                    .board(board)
                    .replyText("댓글........")
                    .replyer("replyer1")
                    .build();

            replyRepository.save(reply);
        }


    }

}
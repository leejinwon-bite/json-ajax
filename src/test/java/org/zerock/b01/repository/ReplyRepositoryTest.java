package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.Reply;

@SpringBootTest
@Log4j2
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public  void testInsert() {

        Long bno = 101L;

        Board board = Board.builder().bno(bno).build();


            Reply reply = Reply.builder()
                    .board(board)
                    .replyText("댓글........")
                    .replyer("replyer1")
                    .build();

            replyRepository.save(reply);


    }

    @Test
    public void testBoardReplies() {

        Long bno = 100L;

        Pageable pageable = PageRequest.of(0,4, Sort.by("rno").ascending());

        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

        log.info("100번 게시물에 있는 size만큼의 댓글 갯수: "+result.getContent()+"이란다.");
    }

}
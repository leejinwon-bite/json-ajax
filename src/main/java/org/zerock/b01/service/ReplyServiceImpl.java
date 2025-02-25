package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.Reply;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;
import org.zerock.b01.repository.ReplyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    private final ModelMapper modelMapper;

    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = modelMapper.map(replyDTO, Reply.class);

        Board bno = Board.builder()
                .bno(replyDTO.getBno())
                .build();

        Reply reply2 = Reply.builder()
                .board(bno)
                .replyText(reply.getReplyText())
                .replyer(reply.getReplyer())
                .build();

        Long rno = replyRepository.save(reply2).getRno();

        return rno;
    }

    @Override
    public ReplyDTO read(Long rno) {

        Optional<Reply> replyOptional = replyRepository.findById(rno);
        // board_bno 자체를 출력 안함. 아마 Entity에서 필드의 자료형이 Board라서 그런가봄ㅋㅋㅋ.
//        애초에 board_bno가 fk 가 되니까, 자료형은 무조건 Board로 해야하는것 같음.
//        아마도 left join 쿼리 메서드를 사용하면, 테이블을 2개를 다루니깐 나올수도 잇을 것 같음.
//        DB에는 조인도 되고, reply 테이블 조회해도 나옴. 아마도 자바쪽에서 자료형 문제일것 같음.
//        queryDSL이 자바를 사용하기 때문에 쪼여주는 아주 조여주는 조여정 같음.
//        Optional<Reply> replyOptional = replyRepository.findById(rno); 조회하는 쿼리 메서드의 자료형이 Reply
//        board_bno의 자료형은 Board.
        Reply reply = replyOptional.orElseThrow();

        log.info(reply);

//        reply의 자료형을 Board로 바꿔준 값 =
//        Board(bno=null, title=null, content=null, writer=null). 여기에 board_bno, bno가 null이거나 없음.
//        개노답임.
        Board board = modelMapper.map(reply,Board.class);

        log.info(board);

        ReplyDTO dto = modelMapper.map(reply, ReplyDTO.class);

        log.info(dto);

        return modelMapper.map(reply, ReplyDTO.class);
    }

    @Override
    public void modify(ReplyDTO replyDTO) {

        Optional<Reply> replyOptional = replyRepository.findById(replyDTO.getRno());

        Reply reply = replyOptional.orElseThrow();

        reply.changeTest(replyDTO.getReplyText());

        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <=0? 0:
                pageRequestDTO.getPage() -1, pageRequestDTO.getSize(), Sort.by("rno").ascending());


        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

        List<ReplyDTO> dtoList = result.getContent().stream().map(reply-> modelMapper.map(reply,ReplyDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}

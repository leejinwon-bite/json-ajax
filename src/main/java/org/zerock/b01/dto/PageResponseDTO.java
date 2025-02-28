package org.zerock.b01.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {

    private int page;
    private int size;
    private int total;

    //시작 페이지 번호
    private int start;
    //끝 페이지 번호
    private int end;

    //이전 페이지의 존재 여부
    private boolean prev;
    //다음 페이지의 존재 여부
    private boolean next;

    private List<E> dtoList;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total){

        if(total <= 0){
            return;
        }

//        this.page = pageRequestDTO.getPage(); = 1
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;

//    console.log("endNum, 페이지 끝 번호: "+endNum+" = 전체 페이지 갯수를 나누는 숫자는 Math.ceil(pageNum/n)*m 에서 m임");
//    console.log("1 페이지당 나열되는 페이지 번호. 다음 페이지는 번호는 누적됨."+endNum); (진원이의 라이브러리 참조)
        this.end =   (int)(Math.ceil(this.page / 10.0 )) *  10;

//   console.log("startNum, 페이지 첫번째 번호: "+startNum+" = var startNum = endNum-m-1;에서 m-1을
//   위하고 맞춰줘야 첫 페이지가 1 이됨.");
//   위는 var endNum = Math.ceil(pageNum/10)*8;
        this.start = this.end - 9;

        int last =  (int)(Math.ceil((total/(double)size)));

        this.end =  end > last ? last: end;

//        1 페이지가 아니면 true, 1페이지 랑 같거나 작으면?(그럴릴 거의 없음) false.
        this.prev = this.start > 1;

//        total은 getTotalElements를 의미하는 것 같음. end는 View page 보이는 페이지 번호의 마지막 번호 인것 같음
//        11페이지 있고 10 페이지씩 페이지 번호 보이게 하면 10이 end임. size는 한 페이지당 list 촤대 갯수 말함.
//        10*10=100, 11페이지에 1개의 레코드가 있으면 101>100=true가 됨. next 버튼이 생김/ 만약 맨 마지막 페이지가
//        9이고, size가 10이면 90이 됨. total은 90이라 false가 됨/
        this.next =  total > this.end * this.size;

    }
}

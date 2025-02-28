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

// 만약 total이 db안에 있는 모든 레코드 갯수인 totalElements라고 한다면, last는 paging 번호중에서도 제일 마지막 번호 1개를
//  의미함. size value 값을 넘으면, 1페이지가 새로 만들어 짐. Math.ceil이 5.1만을 입력 하더라도 6이 나오니깐, paging에 사용.
        int last =  (int)(Math.ceil((total/(double)size)));

//   end는 보여질 수 있는 가장 마지막 페이지임. 누적 되는 애임. 얘가 last 보다 크다는 의미는, 맨 마지막 페이지를 볼 수있는
//   곳에서 가장 마지막 페이지 번호에 닿았다는 거임. 아니라면 불충분한 db레코드로 마지막 페이지까지 닿지 못해서 몇개 달랑 잇는걸
//        말함.
        this.end =  end > last ? last: end;


//        1 페이지가 아니면 true, 1페이지 랑 같거나 작으면?(그럴릴 거의 없음) false.
//        같은 의미론 this.prev = this.start != 1 하고 교체 되는 것도 가능함.
        this.prev = this.start > 1;
//        this.prev = this.start != 1;


//        total은 getTotalElements를 의미하는 것 같음. end는 View page 보이는 페이지 번호의 마지막 번호 인것 같음
//        11페이지 있고 10 페이지씩 페이지 번호 보이게 하면 10이 end임. size는 한 페이지당 list 촤대 갯수 말함.
//        10*10=100, 11페이지에 1개의 레코드가 있으면 101>100=true가 됨. next 버튼이 생김/ 만약 맨 마지막 페이지가
//        9이고, size가 10이면 90이 됨. total은 90이라 false가 됨/
        this.next =  total > this.end * this.size;

    }
}

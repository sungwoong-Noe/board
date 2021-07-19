package simpleboard.board.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity //데이터베이스 연동을 위한 모델 클래스
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment를 사용하면 자동으로 게시글 번호가 증가 되도록 해주는 어노테이션
    private Long id;

    @NotNull
    @Size(min=2, max=30, message = "제목은 2글자 이상 30글자 이하여야합니다.")    //어노테이션으로 사이즈 제약두면 편하지만 자유도 제약있음, 새로운클래스 생성
    private String title;
    private String content;

}

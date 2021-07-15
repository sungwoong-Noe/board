package simpleboard.board.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //데이터베이스 연동을 위한 모델 클래스
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment를 사용하면 자동으로 게시글 번호가 증가 되도록 해주는 어노테이션
    private Long id;
    private String title;
    private String content;

}

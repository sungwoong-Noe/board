package simpleboard.board.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role {     //권한 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment를 사용하면 자동으로 게시글 번호가 증가 되도록 해주는 어노테이션
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")     //User 클래스의 roles
    private List<User> user;

    
}

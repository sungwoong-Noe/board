package simpleboard.board.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {     //사용자 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment를 사용하면 자동으로 게시글 번호가 증가 되도록 해주는 어노테이션
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "user_role",     //Join 테이블: user_role
            joinColumns = @JoinColumn(name = "user_id"),    //user_role 테이블의 user_id
            inverseJoinColumns = @JoinColumn(name = "role_id")     //role테이블의 role_id

    )

    //Many to Many 매핑 하기 위해 JPA 사용, 리스트나 Set 사용
    private List<Role> roles = new ArrayList<>();   //멤버 변수 생성, UserRepository를 이용해서 조회하면 권한정보가 roles에 자동으로 담기게 됨
}

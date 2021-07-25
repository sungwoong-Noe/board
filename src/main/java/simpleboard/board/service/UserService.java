package simpleboard.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import simpleboard.board.model.Role;
import simpleboard.board.model.User;
import simpleboard.board.repository.UserRepository;

@Service        //service 어노테이션으로 비지니스 로직을 작성 할 수 있음
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public User save(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());       //인코더를 활용해서 비밀번호 암호화
        user.setPassword(encodedPassword);                                         //저장
        user.setEnabled(true);
        Role role = new Role();
        role.setId(1l);
        user.getRoles().add(role);                  //권한이 user_role 테이블에 저장됨
        return userRepository.save(user);
    }

}

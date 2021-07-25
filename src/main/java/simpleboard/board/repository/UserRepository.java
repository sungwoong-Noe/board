package simpleboard.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import simpleboard.board.model.Board;
import simpleboard.board.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


}

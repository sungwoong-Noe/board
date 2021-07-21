package simpleboard.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import simpleboard.board.model.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);      //인터페이스만 정의를 하면 구현은 알아서 됨, 컨트롤러에서 인터페이스 호출만 해주면 된다

}

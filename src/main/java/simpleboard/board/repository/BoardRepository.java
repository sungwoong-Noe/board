package simpleboard.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import simpleboard.board.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}

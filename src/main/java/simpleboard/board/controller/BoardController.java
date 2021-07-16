package simpleboard.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import simpleboard.board.model.Board;
import simpleboard.board.repository.BoardRepository;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired //Autowired에 의해 서버 기동될 때 인스턴스 들어옴
    private BoardRepository boardRepository;
    //BoardRepository를 이용해서 테이블의 데이터를 가져옴

    @GetMapping("/list")
    public String list(Model model){ //데이터값을 전해주려면 Model을 추가하면됨
        List<Board> boards = boardRepository.findAll(); //boards라는 데이터 가져옴
        long count = boardRepository.count();
        model.addAttribute("boards", boards); //모델에 담긴 데이터들은 thymeleaf를 이용해서 사용할 수 있음
        model.addAttribute("count", count);
        return "board/list";
    }

}

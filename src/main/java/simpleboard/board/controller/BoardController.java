package simpleboard.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import simpleboard.board.model.Board;
import simpleboard.board.repository.BoardRepository;
import simpleboard.board.validator.BoardValidator;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired //Autowired에 의해 서버 기동될 때 인스턴스 들어옴
    private BoardRepository boardRepository;
    //BoardRepository를 이용해서 테이블의 데이터를 가져옴

    @Autowired
    private BoardValidator boardValidator;



    @GetMapping("/list")
    public String list(Model model){ //데이터값을 전해주려면 Model을 추가하면됨
        List<Board> boards = boardRepository.findAll(); //boards라는 데이터 가져옴
        long count = boardRepository.count();
        model.addAttribute("boards", boards); //모델에 담긴 데이터들은 thymeleaf를 이용해서 사용할 수 있음
        model.addAttribute("count", count);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id == null){
            model.addAttribute("board", new Board());
        }else{
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String greetingSubmit(@Valid Board board, BindingResult bindingResult){  //보드를 받아오는 컨트롤러 작성
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()){
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list";
    }


}


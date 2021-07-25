package simpleboard.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    //Autowired에 의해 서버 기동될 때 인스턴스 들어옴
    private final BoardRepository boardRepository;
    //BoardRepository를 이용해서 테이블의 데이터를 가져옴

    private final BoardValidator boardValidator;

    public BoardController(BoardRepository boardRepository, BoardValidator boardValidator) {
        this.boardRepository = boardRepository;
        this.boardValidator = boardValidator;
    }


    @GetMapping("/list")
    public String list(Model model, Pageable pageable){ //데이터값을 전해주려면 Model을 추가하면됨
        Page<Board> boards = boardRepository.findAll(pageable); //Pageable로 페이지 값과 사이즈를 요청할 수 있게됨
        int startPage = Math.max(0, boards.getPageable().getPageNumber() - 4);  //Math.max 클래스를 활용해서 최대 페이지 개수
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);
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


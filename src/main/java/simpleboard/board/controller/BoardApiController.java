package simpleboard.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import simpleboard.board.model.Board;
import simpleboard.board.repository.BoardRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
class BoardApiController {

    @Autowired
    private BoardRepository repository;

    @GetMapping("/boards")
    List<Board> all(){
       return repository.findAll();     //전체적인 게시판 내용 불러옴
    }


    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard){
        return repository.save(newBoard);       //포스트 요청시 새로운 데이터 생성

    }

    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id){
        return repository.findById(id).orElse(null);   //GetMapping에 id를 할 수 있음, /boards/{id}를 하면 id에 맞는 게시판으로 이동
    }

    @PostMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id){

        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id){
        repository.deleteById(id);
    }
}

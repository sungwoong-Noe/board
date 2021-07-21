package simpleboard.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import simpleboard.board.model.Board;
import simpleboard.board.repository.BoardRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
class BoardApiController {

    @Autowired
    private BoardRepository repository;

    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false) String title){   //제목으로 검색하는 API
        if(StringUtils.isEmpty(title)){              //전달이 안됐을 시
            return repository.findAll();             //전체적인 게시판 내용 불러옴
        }else{                                       //전달이 됐다면 검색 > 검색하는 메소드를 BoardRepository에 추가
            return repository.findByTitle(title);    //검색해서 title과 일치하는 게시글 리턴됨
        }
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

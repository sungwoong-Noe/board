package simpleboard.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller //컨트롤러 어노테이션
public class HomeController {

    @GetMapping("/index") //브라우저 매핑
    public String index() {
        return "index";
    }
}

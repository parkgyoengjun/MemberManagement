package com.example.demo.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // Spring MVC에서 웹 애플리케이션의 컨트롤러를 정의하는 데 사용되는 애너테이션
            // 클라이언트로부터 들어오는 HTTP 요청을 처리
//@RequestMapping("/")
public class HomeController {

    // 기본페이지 요청 메서드
    @GetMapping("/")
    public String index() {
        return "index"; // -> templates 폴더의 index.html 을 찾아감
    }

//    @GetMapping("/board")
//    public String board() {
//        return "board";
//    }
}

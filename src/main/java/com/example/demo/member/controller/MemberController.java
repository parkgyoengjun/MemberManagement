package com.example.demo.member.controller;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;

    // 회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

//    @PostMapping("/member/save")
//    public String save(@RequestParam("memberEmail") String memberEmail,
//                       @RequestParam("memberPassword") String memberPassword,
//                       @RequestParam("memberName") String memberName) {
//        System.out.println("MemberController.save @@@@@@@");
//        System.out.println("memberEmail = " + memberEmail + ", memberPassword = " + memberPassword + ", memberName = " + memberName);
//        return "index";
//    }  밑에랑 같음

    /*
    @ModelAttribute
     웹 애플리케이션에서 모델 데이터를 바인딩하는데 사용
     주로 컨트롤러 메서드의 매개변수나 메서드에 적용되어,
     요청 파라미터를 모델 객체에 매핑하거나,특정 데이터를 뷰에 전달하는 데 사용됩니다.
    */
    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("MemberController.save @@@@@@@");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "index";
    }

    @GetMapping("/member/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        // HttpSession : Java Servlet API에서 사용되며, 서버는 클라이언트의 상태를 유지하기 위해 세션 객체를 생성하고 관리
        // 로그인 된 정보를 계속 이용하기 위해 HttpSession 을 이용
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        } else {
            // login 실패
            return "login";
        }
    }

    @GetMapping("/member/")
    public String findAll(Model model) { // 정보들을 담아서 보내야하니까 GetMapping 에서
        List<MemberDTO> memberDTOList = memberService.findAll();
        // 목록이니까 List<> 형태

        model.addAttribute("memberList", memberDTOList);
        // memberList 는 list.html 의 member:${memberList} 를 담당, 아마 반복문이여서 다른듯
        // 어떠한 html 로 가져갈 데이터가 있다면 model 사용
        return "list";
    }

    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model) {
//        @PathVariable 경로 상의 값을 가져올때 사용
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        // "member" 는 detail.html 의 ${member.id}의 member 에 쓰임
        return "detail";
        // 위의 조건을 더 걸어서 만들수도 있음
    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model) { // 새로운 정보를 받았으니까 전달해주기위해
        // Model은 주로 Spring MVC에서 사용되는 객체로, 컨트롤러와 뷰(템플릿) 간에 데이터를 전달하는 데 사용
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO dto) {
        memberService.update(dto);
        return "redirect:/member/" + dto.getId(); // -> 이러면 위에 @GetMapping("/member/{id})를 가게됨
    }
    /*
    Forward
URL이 변경되지 않음.
서버 내부에서 요청을 처리.
클라이언트는 이전 URL을 계속 보게 됨.
forward - http://localhost:8082/member/delete/5

    Redirect
URL이 변경됨.
클라이언트에게 새로운 요청을 보냄.
클라이언트는 새로운 URL을 보게 됨.
redirect - http://localhost:8082/member/save
    */

    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/member/";
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        // session.invalidate()는 Java Servlet API에서 사용되는 메서드로, 현재 세션을 무효화(invalidate)하는 기능을 수행
        return "index";
    }

    @PostMapping("/member/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        System.out.println("memberEmail = " + memberEmail);
//        return "체크완료";
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
//        if (checkResult != null) {
//            return "ok";
//        } else {
//            return "no";
//        }
    }
}

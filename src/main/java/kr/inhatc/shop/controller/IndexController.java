package kr.inhatc.shop.controller;

import kr.inhatc.shop.config.auth.PrincipalDetails;
import kr.inhatc.shop.entity.Member;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal PrincipalDetails userDetails, Model model) {
        if(userDetails != null) {
            Member member = userDetails.getMember();
            model.addAttribute("message", "사용자 이름은 " + member.getName() + "이고, 권한은 " + member.getRole().toString() + " 입니다.");
        } else {
            model.addAttribute("message", "로그인 전 입니다.");
        }
        return "index";
    }
}

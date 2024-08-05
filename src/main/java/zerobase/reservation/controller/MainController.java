package zerobase.reservation.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    // 메인 화면
    @RequestMapping("/")
    public String dashboard(@AuthenticationPrincipal User user, Model model) {
        //AuthenticationPrincipal 유저 정보 가져오기
        model.addAttribute("user", user);

        return "main/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @PostMapping("/logout")
    public String logout() {
        return "account/login";
    }
}

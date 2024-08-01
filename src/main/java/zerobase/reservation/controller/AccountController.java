package zerobase.reservation.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zerobase.reservation.domain.UserEntity;
import zerobase.reservation.dto.Auth;
import zerobase.reservation.service.AccountService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/customer-join")
    public String customerJoin() {
        return "account/customer-join";
    }

    @GetMapping("/manager-join")
    public String managerJoin() {
        return "account/manager-join";
    }

    // 회원 가입
    @PostMapping(value = {"/customer-join", "/manager-join"})
    public String join(
            Auth.join join,
            HttpServletRequest request
    ) {
        UserEntity userEntity = accountService.register(join, request.getRequestURI());

        log.info(userEntity.toString());
        return "redirect:/account/login";
    }

    // 회원 이메일 인증
    @GetMapping("/email-auth")
    public ResponseEntity<?> emailAuth(@RequestParam("id") String uuid) {
        UserEntity emailAuth = accountService.emailAuth(uuid);

        log.info("valid email auth -> " + emailAuth.getId());

        return ResponseEntity.ok("이메일 인증 완료");
    }

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @PostMapping("/logout")
    public String logout() {
        return "account/login";
    }

    @RequestMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal User user, Model model) {
        //AuthenticationPrincipal 유저 정보 가져오기
        model.addAttribute("user", user);

        return "account/dashboard";
    }
}
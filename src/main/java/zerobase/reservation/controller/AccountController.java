package zerobase.reservation.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zerobase.reservation.domain.AccountEntity;
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
        AccountEntity accountEntity = accountService.register(join, request.getRequestURI());

        log.info(accountEntity.toString());
        return "redirect:/account/login";
    }

    // 회원 이메일 인증
    @GetMapping("/email-auth")
    public ResponseEntity<?> emailAuth(@RequestParam("id") String uuid) {
        AccountEntity emailAuth = accountService.emailAuth(uuid);

        log.info("valid email auth -> " + emailAuth.getId());

        return ResponseEntity.ok("이메일 인증 완료");
    }
}
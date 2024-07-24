package zerobase.reservation.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.domain.UserEntity;
import zerobase.reservation.dto.Auth;
import zerobase.reservation.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;

    // 회원 가입
    @PostMapping(value = {"/user-join", "/admin-join"})
    public ResponseEntity<?> join(
            @RequestBody Auth.join join,
            HttpServletRequest request
    ) {
        UserEntity userEntity = userService.register(join, request.getRequestURI());

        return ResponseEntity.ok(userEntity);
    }

    // 회원 이메일 인증
    @GetMapping("/email-auth")
    public ResponseEntity<?> emailAuth(@RequestParam("id") String uuid) {
        UserEntity emailAuth = userService.emailAuth(uuid);

        log.info("valid email auth -> " + emailAuth.getId());

        return ResponseEntity.ok("이메일 인증 완료");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Auth.login request) {
        userService.authenticate(request);

        log.info("login user -> " + request.getId());

        return ResponseEntity.ok("로그인 성공");
    }
}

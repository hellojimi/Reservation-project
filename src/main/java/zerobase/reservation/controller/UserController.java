package zerobase.reservation.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.domain.UserEntity;
import zerobase.reservation.dto.RegisterUser;
import zerobase.reservation.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> register(
            @RequestBody RegisterUser parameter,
            HttpServletRequest request
    ) {
        UserEntity userEntity = userService.register(parameter, request.getRequestURI());

        return ResponseEntity.ok(userEntity);
    }

    @GetMapping("/email-auth")
    public ResponseEntity<?> emailAuth(@RequestParam("id") String uuid) {
        UserEntity emailAuth = userService.emailAuth(uuid);
        log.info("valid email auth -> " + emailAuth.getId());

        return ResponseEntity.ok("이메일 인증 완료");
    }
}

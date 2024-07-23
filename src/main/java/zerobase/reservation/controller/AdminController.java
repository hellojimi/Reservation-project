package zerobase.reservation.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.reservation.domain.User;
import zerobase.reservation.dto.RegisterUser;
import zerobase.reservation.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> register(@RequestBody RegisterUser parameter, HttpServletRequest request) {
        log.info(parameter.getUserId());

        User admin = userService.register(parameter, request.getRequestURI());

        return ResponseEntity.ok(admin);
    }
}

package zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zerobase.reservation.domain.ReservationEntity;
import zerobase.reservation.domain.RestaurantEntity;
import zerobase.reservation.domain.UserEntity;
import zerobase.reservation.dto.Reservation;
import zerobase.reservation.exception.UserException;
import zerobase.reservation.service.AccountService;
import zerobase.reservation.service.ReservationService;
import zerobase.reservation.service.RestaurantService;
import zerobase.reservation.type.ErrorCode;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final AccountService accountService;
    private final RestaurantService restaurantService;

    // 웨이팅 등록
    @GetMapping("/waiting")
    public String waiting(
            @AuthenticationPrincipal User user,
            @RequestParam("id") Long restaurantId,
            Model model
    ) {
        if (user.getUsername() == null) {
            throw new UserException(ErrorCode.REQUIRED_LOGIN);
        }

        UserEntity accountInfo = accountService.getAccountInfo(user.getUsername());
        RestaurantEntity restaurantInfo = restaurantService.findRestaurant(restaurantId);

        model.addAttribute("accountInfo", accountInfo);
        model.addAttribute("restaurantInfo", restaurantInfo);

        return "reservation/waiting";
    }

    @PostMapping("/waiting")
    public String registerWaiting(
            @RequestParam("restaurantId") Long restaurantId,
            @RequestParam("accountId") String accountId,
            Reservation reservation,
            Model model
    ) {
        ReservationEntity result
                = reservationService.registerWaiting(restaurantId, accountId, reservation);

        if (result == null) {
            throw new UserException(ErrorCode.FAILED_REGISTER_WAITING);
        }

        model.addAttribute("result", result);
        return "/";
    }
}

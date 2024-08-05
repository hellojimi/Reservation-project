package zerobase.reservation.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.domain.ReservationEntity;
import zerobase.reservation.domain.RestaurantEntity;
import zerobase.reservation.domain.AccountEntity;
import zerobase.reservation.dto.ReservationList;
import zerobase.reservation.dto.Reservation;
import zerobase.reservation.exception.Status;
import zerobase.reservation.service.AccountService;
import zerobase.reservation.service.ReservationService;
import zerobase.reservation.service.RestaurantService;
import zerobase.reservation.type.ErrorCode;

import java.net.http.HttpRequest;
import java.util.List;

import static zerobase.reservation.config.security.annotation.ManagerAuthorize;

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
            throw new Status(ErrorCode.REQUIRED_LOGIN);
        }

        AccountEntity accountInfo = accountService.getAccountInfo(user.getUsername());
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
        ReservationEntity reservationResult
                = reservationService.registerWaiting(restaurantId, accountId, reservation);

        if (reservationResult == null) {
            throw new Status(ErrorCode.FAILED_REGISTER_WAITING);
        }

        AccountEntity accountInfo = accountService.getAccountInfo(accountId);
        RestaurantEntity restaurantInfo = restaurantService.findRestaurant(restaurantId);

        model.addAttribute("accountInfo", accountInfo);
        model.addAttribute("restaurantInfo", restaurantInfo);
        model.addAttribute("reservationResult", reservationResult);

        return "reservation/myReservation";
    }

    // 내 예약 내역 조회
    @GetMapping("/myReservationList")
    public String getMyReservationList(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        List<ReservationList> myReservationList
                = reservationService.getMyReservationList(user.getUsername());

        model.addAttribute("myReservationList", myReservationList);

        return "reservation/myReservationList";
    }

    // 관리자 예약 내역 조회
    @GetMapping("/list")
    @ManagerAuthorize
    public String getManagerRestaurantReservationList(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        Object result
                = reservationService.getManagerReservationList(user.getUsername());

        if (result == null) {
            throw new Status(ErrorCode.NOT_FOUND_RESERVATION_INFO);
        }

        model.addAttribute("result", result);
        return "reservation/managerReservationList";
    }

    // 관리자 예약 승인 여부
    @PostMapping("/approve")
    @ManagerAuthorize
    public String approvalByManager(HttpServletRequest request) {
        Long reservationId = Long.valueOf(request.getParameter("reservationId"));
        String status = request.getParameter("status");

        reservationService.getReservationApproveResult(reservationId, status);

        return "redirect:list";
    }

    // 현장 예약 확인
    @GetMapping("/kiosk")
    public String checkReservation(
            @RequestParam("id") Long restaurantId,
            Model model
    ) {
        RestaurantEntity restaurantInfo = restaurantService.findRestaurant(restaurantId);
        model.addAttribute("restaurantInfo", restaurantInfo);

        return "reservation/kiosk";
    }

    @PostMapping("/kiosk")
    public String checkReservationSubmit(
            @RequestParam("restaurantId") Long restaurantId,
            @RequestParam("phone") String phone,
            Model model
    ) {
        ReservationEntity reservation
                = reservationService.checkReservationUsingKiosk(restaurantId, phone);

        AccountEntity accountInfo = accountService.getAccountInfo(reservation.getCustomerId());

        model.addAttribute("reservation", reservation);
        model.addAttribute("accountInfo", accountInfo);

        return "reservation/kiosk-result";
    }
}

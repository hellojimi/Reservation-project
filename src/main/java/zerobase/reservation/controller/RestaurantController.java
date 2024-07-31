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
import zerobase.reservation.domain.RestaurantEntity;
import zerobase.reservation.dto.Restaurant;
import zerobase.reservation.exception.UserException;
import zerobase.reservation.service.RestaurantService;
import zerobase.reservation.type.ErrorCode;

import java.util.List;

import static zerobase.reservation.config.security.annotation.ManagerAuthorize;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    // 매장 등록
    @GetMapping("/register")
    @ManagerAuthorize
    public String restaurant(@AuthenticationPrincipal User user) {
        if (user == null) {
            throw new UserException(ErrorCode.REQUIRED_LOGIN);
        }

        log.info("관리자 정보: " + user.getUsername());

        return "restaurant/register";
    }

    @PostMapping("/register")
    public String registerRestaurant(
            @AuthenticationPrincipal User user,
            Restaurant restaurant,
            Model model
    ) {
        RestaurantEntity result = restaurantService.register(user.getUsername(), restaurant);
        model.addAttribute("restaurantData", result.getName());

        return "redirect:/restaurant/registeredList";
    }

    // 관리자가 등록한 매장 목록
    @GetMapping("/registeredList")
    @ManagerAuthorize
    public String registeredRestaurantList(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        List<RestaurantEntity> restaurantEntities
                = restaurantService.registeredRestaurantList(user.getUsername());
        model.addAttribute("restaurantEntities", restaurantEntities);

        return "restaurant/registeredList";
    }

    // 전체 매장 목록
    @GetMapping("/list")
    public String restaurantList(Model model) {
        List<RestaurantEntity> restaurantEntities = restaurantService.restaurantList();
        model.addAttribute("restaurantEntities", restaurantEntities);

        return "restaurant/list";
    }

    // 매장 상세 내용
    @GetMapping("/details")
    public String restaurantDetails(
            @RequestParam("id") Long restaurantId,
            Model model
    ) {
        RestaurantEntity restaurant = restaurantService.findRestaurant(restaurantId);
        model.addAttribute("restaurant", restaurant);

        return "restaurant/details";
    }

    // 매장 정보 수정
    @GetMapping("/update")
    @ManagerAuthorize
    public String updateRestaurant(
            @RequestParam("id") Long restaurantId,
            Model model
    ) {
        RestaurantEntity restaurant = restaurantService.findRestaurant(restaurantId);
        model.addAttribute("restaurant", restaurant);

        return "restaurant/update";
    }

    @PostMapping("/update")
    public String updateRestaurant(
            @RequestParam("id") Long restaurantId,
            Restaurant restaurant
    ) {
        restaurantService.update(restaurantId, restaurant);

        return "redirect:/restaurant/registeredList";
    }

    // 매장 삭제
    @GetMapping("/delete")
    @ManagerAuthorize
    public String deleteRestaurant(@RequestParam("id") Long restaurantId) {
        restaurantService.delete(restaurantId);

        return "restaurant/delete";
    }
}

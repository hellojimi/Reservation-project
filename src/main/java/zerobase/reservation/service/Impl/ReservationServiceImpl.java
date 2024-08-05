package zerobase.reservation.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerobase.reservation.domain.ReservationEntity;
import zerobase.reservation.domain.RestaurantEntity;
import zerobase.reservation.domain.AccountEntity;
import zerobase.reservation.dto.Reservation;
import zerobase.reservation.dto.ReservationList;
import zerobase.reservation.exception.Status;
import zerobase.reservation.repository.AccountRepository;
import zerobase.reservation.repository.ReservationRepository;
import zerobase.reservation.repository.RestaurantRepository;
import zerobase.reservation.service.ReservationService;
import zerobase.reservation.type.ErrorCode;
import zerobase.reservation.type.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final RestaurantRepository restaurantRepository;
    private final AccountRepository accountRepository;
    private final ReservationRepository reservationRepository;

    // 웨이팅 등록하기
    @Override
    public ReservationEntity registerWaiting(
            Long restaurantId, String accountId, Reservation reservation
    ) {
        // 입력한 날짜 유효성 확인
        validateDateTime(reservation);

        // 중복 예약 내역 존재 여부 확인
        validateDuplicateReservation(accountId, reservation);

        return reservationRepository.save(Reservation.of(restaurantId, accountId, reservation));
    }

    // 현장 예약 확인
    public ReservationEntity checkReservationUsingKiosk(Long restaurantId, String phone) {
        // 전화번호로 예약자 정보 확인
        AccountEntity reservationPersonInfo = accountRepository.findByPhone(phone)
                .orElseThrow(() -> new Status(ErrorCode.NOE_FOUND_RESERVATION_PERSON_INFO));

        // 오늘 날짜 예약 내역 확인
        String date = String.valueOf(LocalDate.now());
        ReservationEntity reservationByToday
                = reservationRepository.findTop1ByRestaurantIdAndCustomerIdAndReservationDateOrderByReservationTime(
                        restaurantId, reservationPersonInfo.getId(), date)
                .orElseThrow(() -> new Status(ErrorCode.NOT_FOUND_RESERVATION_INFO));

        // 예약 10분전 도착 확인
        return checkingArrivalTime(reservationByToday);
    }

    // 예약자 예약 내역 전체 조회
    public List<ReservationList> getMyReservationList(String accountId) {
        List<ReservationEntity> myReservationList
                = reservationRepository.findAllByCustomerId(accountId);

        if (myReservationList.isEmpty()) {
            throw new Status(ErrorCode.NOT_FOUND_RESERVATION_INFO);
        }

        // 예약자 상세 정보
        AccountEntity userInfo = accountRepository.findById(accountId)
                .orElseThrow(() -> new Status(ErrorCode.NOT_FOUND_USER));

        List<ReservationList> reservationList = new ArrayList();
        for (ReservationEntity reservation : myReservationList) {
            // 매장 정보
            RestaurantEntity restaurant = restaurantRepository.findById(reservation.getRestaurantId())
                    .orElseThrow(() -> new Status(ErrorCode.NOT_FOUND_RESTAURANT));

            reservationList.add(ReservationList.of(reservation, userInfo, restaurant));
        }

        return reservationList;
    }

    // 관리자 예약 내역 조회
    @Override
    public Object getManagerReservationList(String managerId) {
        // 관리자 아이디로 식당 정보 가져오기
        List<RestaurantEntity> restaurantList
                = restaurantRepository.findAllByManagerId(managerId);

        if (restaurantList.isEmpty()) {
            throw new Status(ErrorCode.NOT_FOUND_RESTAURANT);
        }

        List<ReservationList> result = new ArrayList();
        // 식당 아이디로 예약 정보 가져오기
        for (RestaurantEntity restaurant : restaurantList) {
            List<ReservationEntity> reservationList
                    = reservationRepository.findAllByRestaurantId(restaurant.getId());

            for (ReservationEntity reservation : reservationList) {
                // 예약자 정보 가져오기
                AccountEntity customerInfo = accountRepository.findById(reservation.getCustomerId())
                        .orElseThrow(() -> new Status(ErrorCode.NOT_FOUND_USER));

                result.add(ReservationList.of(reservation, customerInfo, restaurant));
            }
        }

        return result;
    }

    // 예약 승인 여부
    public void getReservationApproveResult(Long reservationId, String status) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new Status(ErrorCode.NOT_FOUND_RESERVATION_INFO));

        if ("승인".equals(status)) {
            reservationRepository.save(Reservation.updateConfirm(reservationEntity));
        } else if ("취소".equals(status)) {
            reservationRepository.save(Reservation.updateCancel(reservationEntity));
        }
    }

    // 입력한 날짜 유효성 확인
    private void validateDateTime(Reservation reservation) {
        // 현재 날짜 및 시간
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        // 입력된 날짜 변환
        ArrayList transferredDateTime = transferDateTime(reservation);
        ChronoLocalDate parsedDate = (ChronoLocalDate) transferredDateTime.getFirst();
        LocalTime parsedTime = (LocalTime) transferredDateTime.getLast();

        if (parsedDate.isBefore(date)) {
            throw new Status(ErrorCode.NOT_ALLOW_TODAY_BEFORE_DATE);
        } else if (parsedDate.isEqual(date) && parsedTime.isBefore(time)) {
            throw new Status(ErrorCode.NOT_ALLOW_NOW_BEFORE_TIME);
        }
        // 영업시간 외

        getClear(transferredDateTime);
    }

    // 중복 예약 내역 존재 여부 확인
    private void validateDuplicateReservation(
            String accountId, Reservation reservation
    ) {
        ArrayList transferredDateTime = transferDateTime(reservation);
        String parsedDate = String.valueOf(transferredDateTime.getFirst());
        String parsedTime = String.valueOf(transferredDateTime.getLast());

        if (reservationRepository.existsByCustomerIdAndReservationDateAndReservationTime(
                accountId, parsedDate, parsedTime)
        ) {
            throw new Status(ErrorCode.FAILED_TO_RESERVE_BY_DUPLICATE);
        }

        getClear(transferredDateTime);
    }

    // 예약 10분전 도착 확인
    private ReservationEntity checkingArrivalTime(ReservationEntity reservation) {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime tenToReservationTime
                = LocalTime.parse(reservation.getReservationTime(), timeFormat).minusMinutes(10);

        LocalTime now = LocalTime.now();

        if (now.isAfter(tenToReservationTime)) {
            reservation.setReservationStatus(ReservationStatus.CANCEL);
            reservationRepository.save(reservation);

            throw new Status(ErrorCode.CANCELED_RESERVATION);
        }

        return reservation;
    }

    // 변환된 날짜 set
    private ArrayList transferDateTime(Reservation reservation) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        // 날짜 변환
        LocalDate parseDate = LocalDate.parse(reservation.getReservationDate(), dateFormat);
        LocalTime parseTime = LocalTime.parse(reservation.getReservationTime(), timeFormat);

        ArrayList dateTimeSet = new ArrayList<>();
        dateTimeSet.add(parseDate);
        dateTimeSet.add(parseTime);

        return dateTimeSet;
    }

    // 날짜 데이터 지우기
    private void getClear(ArrayList transferredDateTime) {
        if (!transferredDateTime.isEmpty())
            transferredDateTime.clear();
    }
}

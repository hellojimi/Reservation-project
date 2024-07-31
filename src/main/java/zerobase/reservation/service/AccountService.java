package zerobase.reservation.service;

import zerobase.reservation.domain.UserEntity;
import zerobase.reservation.dto.Auth;

public interface AccountService {

    UserEntity register(Auth.join join, String requestURI);

    // uuid로 이메일 인증 확인
    UserEntity emailAuth(String uuid);

}

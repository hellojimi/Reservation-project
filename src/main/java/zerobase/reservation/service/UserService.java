package zerobase.reservation.service;

import zerobase.reservation.domain.UserEntity;
import zerobase.reservation.dto.Auth;

import java.util.Optional;

public interface UserService {

    UserEntity register(Auth.join join, String requestURI);

    Optional<UserEntity> findOne(String id);

    // uuid로 이메일 인증 확인
    UserEntity emailAuth(String uuid);

    UserEntity authenticate(Auth.login login);

}

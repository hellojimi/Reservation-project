package zerobase.reservation.service;

import zerobase.reservation.domain.UserEntity;
import zerobase.reservation.dto.RegisterUser;

import java.util.Optional;

public interface UserService {

    UserEntity register(RegisterUser parameter, String requestURI);

    Optional<UserEntity> findOne(String id);

    // 이메일 인증 확인
    UserEntity emailAuth(String uuid);

}

package zerobase.reservation.service;

import zerobase.reservation.domain.User;
import zerobase.reservation.dto.RegisterUser;

import java.util.Optional;

public interface UserService {

    User register(RegisterUser parameter, String requestURI);

    Optional<User> findOne(String id);

}

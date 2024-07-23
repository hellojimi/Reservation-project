package zerobase.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.reservation.domain.User;
import zerobase.reservation.dto.RegisterUser;
import zerobase.reservation.exception.UserException;
import zerobase.reservation.repository.UserRepository;
import zerobase.reservation.type.ErrorCode;
import zerobase.reservation.type.UserAuth;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterUser registerUser, String requestURI) {

        validateDuplicateUser(registerUser);

        UserAuth role = UserAuth.USER;
        if("/admin/join".equals(requestURI)) {
            role = UserAuth.OWNER;
        }

        User user = User.builder()
                .id(registerUser.getUserId())
                .name(registerUser.getName())
                .phone(registerUser.getPhone())
                .password(passwordEncoder.encode(registerUser.getPassword()))
                .userType(role)
                .registerAt(LocalDateTime.now())
                // 수정하기
                .emailAuthYn(false)
                .emailAuthKey(null)
                .emailAuthDt(null)
                .build();

        User result = userRepository.save(user);

        return result;
    }

    private void validateDuplicateUser(RegisterUser registerUser) {
        boolean exists = userRepository.existsById(registerUser.getUserId());
        if (exists) {
            throw new UserException(ErrorCode.DUPLICATE_USER);
        }
    }

    @Override
    public Optional<User> findOne(String id) {
        return userRepository.findById(id);
    }
}

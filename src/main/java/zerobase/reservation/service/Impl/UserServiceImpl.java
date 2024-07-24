package zerobase.reservation.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.reservation.config.mail.MailComponents;
import zerobase.reservation.domain.UserEntity;
import zerobase.reservation.dto.Auth;
import zerobase.reservation.exception.UserException;
import zerobase.reservation.repository.UserRepository;
import zerobase.reservation.service.UserService;
import zerobase.reservation.type.ErrorCode;
import zerobase.reservation.type.UserAuth;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailComponents mailComponents;

    @Override
    public UserEntity register(Auth.join registerUser, String requestURI) {

        validateDuplicateUser(registerUser);

        UserAuth role = UserAuth.USER;
        if ("/account/admin-join".equals(requestURI)) {
            role = UserAuth.OWNER;
        }

        String uuid = UUID.randomUUID().toString();

        UserEntity result = userRepository.save(
                UserEntity.builder()
                        .id(registerUser.getUserId())
                        .name(registerUser.getName())
                        .phone(registerUser.getPhone())
                        .password(passwordEncoder.encode(registerUser.getPassword()))
                        .userType(role)
                        .registerAt(LocalDateTime.now())
                        .emailAuthYn(false)
                        .emailAuthKey(uuid)
                        .build()
        );

        // 이메일 발송
        boolean resultSendMail = sendMailToUser(registerUser.getUserId(), uuid);
        if (!resultSendMail) {
            throw new UserException(ErrorCode.FAILED_SEND_MAIL);
        }

        log.info("이메일 발송 완료 -> " + registerUser.getUserId());

        return result;
    }

    @Override
    public UserEntity emailAuth(String uuid) {
        UserEntity userEntity = userRepository.findByEmailAuthKey(uuid)
                .orElseThrow(() -> new UserException(ErrorCode.UNKNOWN_AUTH_KEY));

        userEntity.setEmailAuthYn(true);
        userEntity.setEmailAuthDt(LocalDateTime.now());
        userRepository.save(userEntity);

        return userEntity;
    }

    //로그인
    @Override
    public UserEntity authenticate(Auth.login login) {
        UserEntity user = userRepository.findById(login.getId())
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new UserException(ErrorCode.UN_MATCHED_PASSWORD);
        }

        return user;
    }

    private void validateDuplicateUser(Auth.join registerUser) {
        boolean exists = userRepository.existsById(registerUser.getUserId());
        if (exists) {
            throw new UserException(ErrorCode.DUPLICATE_USER);
        }
    }

    private boolean sendMailToUser(String toEmail, String uuid) {
        String subject = " 가입을 축하드립니다. ";
        String text =
                "<p>가입을 축하드립니다.</p>" +
                "<p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>" +
                "<div>" +
                    "<a target='_blank' href='http://localhost:8080/user/email-auth?id=" + uuid + "'>이메일 인증</a>" +
                "</div>";

        return mailComponents.sendMail(toEmail, subject, text);
    }

    @Override
    public Optional<UserEntity> findOne(String id) {
        return userRepository.findById(id);
    }
}

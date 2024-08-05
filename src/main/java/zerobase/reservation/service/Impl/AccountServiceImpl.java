package zerobase.reservation.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.reservation.config.mail.MailComponents;
import zerobase.reservation.domain.AccountEntity;
import zerobase.reservation.dto.Auth;
import zerobase.reservation.exception.Status;
import zerobase.reservation.repository.AccountRepository;
import zerobase.reservation.service.AccountService;
import zerobase.reservation.type.ErrorCode;
import zerobase.reservation.type.UserAuth;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailComponents mailComponents;

    @Override
    public AccountEntity register(Auth.join registerUser, String requestURI) {

        // 회원 중복 확인
        validateDuplicateUser(registerUser);

        UserAuth role = UserAuth.CUSTOMER;
        if ("/account/manager-join".equals(requestURI)) {
            role = UserAuth.MANAGER;
        }

        String uuid = UUID.randomUUID().toString();

        AccountEntity result = accountRepository.save(
                AccountEntity.builder()
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

        // 이메일 인증을 위해 이메일 발송
        boolean resultSendMail = sendMailToUser(registerUser.getUserId(), uuid);
        if (!resultSendMail) {
            throw new Status(ErrorCode.FAILED_SEND_MAIL);
        }

        log.info("이메일 발송 완료 -> " + registerUser.getUserId());

        return result;
    }

    // 이메일 인증 확인
    @Override
    public AccountEntity emailAuth(String uuid) {
        AccountEntity accountEntity = accountRepository.findByEmailAuthKey(uuid)
                .orElseThrow(() -> new Status(ErrorCode.UNKNOWN_AUTH_KEY));

        accountEntity.setEmailAuthYn(true);
        accountEntity.setEmailAuthDt(LocalDateTime.now());
        accountRepository.save(accountEntity);

        return accountEntity;
    }

    // 회원 정보 찾기
    @Override
    public AccountEntity getAccountInfo(String accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new Status(ErrorCode.NOT_FOUND_USER));
    }

    // 회원 중복 확인
    private void validateDuplicateUser(Auth.join registerUser) {
        boolean exists = accountRepository.existsById(registerUser.getUserId());
        if (exists) {
            throw new Status(ErrorCode.DUPLICATE_USER);
        }
    }

    // 이메일 발송 form
    private boolean sendMailToUser(String toEmail, String uuid) {
        String subject = " 가입을 축하드립니다. ";
        String text =
                "<p>가입을 축하드립니다.</p>" +
                "<p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>" +
                "<div>" +
                    "<a target='_blank' href='http://localhost:8080/account/email-auth?id=" + uuid + "'>이메일 인증</a>" +
                "</div>";

        return mailComponents.sendMail(toEmail, subject, text);
    }
}

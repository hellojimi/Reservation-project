package zerobase.reservation.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zerobase.reservation.domain.AccountEntity;
import zerobase.reservation.repository.AccountRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("userId -> " + username);

        AccountEntity accountEntity = accountRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다." + username));

        log.info("loadUserByUsername -> " + accountEntity.toString());

        return new UserAdapter(accountEntity); // 시큐리티 세션에 유저 정보가 등록됨
    }
}

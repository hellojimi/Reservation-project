package zerobase.reservation.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import zerobase.reservation.domain.UserEntity;
import zerobase.reservation.service.UserService;

@Component
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findOne(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다." + userId));

        return (UserDetails) UserEntity.builder()
                .id(userEntity.getId())
                .password(userEntity.getPassword())
                .userType(userEntity.getUserType())
                .build();
    }
}

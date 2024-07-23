package zerobase.reservation.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import zerobase.reservation.domain.User;
import zerobase.reservation.service.UserService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> optionalUser = userService.findOne(userId);
        User user = optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("존재하지 않는 회원입니다."));

        return (UserDetails) User.builder()
                .id(user.getId())
                .password(user.getPassword())
                .userType(user.getUserType())
                .build();
    }
}

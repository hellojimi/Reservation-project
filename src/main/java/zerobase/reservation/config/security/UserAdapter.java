package zerobase.reservation.config.security;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import zerobase.reservation.domain.UserEntity;
import zerobase.reservation.type.UserAuth;

import java.util.ArrayList;
import java.util.Collection;


@Getter
@Slf4j
// 로그인 했을 때 정보, 세션에 등록될 정보
public class UserAdapter extends User {

    private final UserEntity userEntity;

    public UserAdapter(UserEntity userEntity) {
        super(userEntity.getId(), userEntity.getPassword(), authorities(userEntity.getUserType()));

        log.info("SecurityUser user.id: " + userEntity.getId());
        log.info("SecurityUser user.password: " + userEntity.getPassword());
        log.info("SecurityUser user.role: " + userEntity.getUserType());

        this.userEntity = userEntity;
    }

    // 계정이 가지고 있는 권한 목록
    private static Collection<? extends GrantedAuthority> authorities(UserAuth userType) {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority("ROLE_" + userType));

        return collection;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getId();
    }
}

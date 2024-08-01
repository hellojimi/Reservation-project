package zerobase.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.reservation.domain.UserEntity;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmailAuthKey(String emailAuthKey);

}

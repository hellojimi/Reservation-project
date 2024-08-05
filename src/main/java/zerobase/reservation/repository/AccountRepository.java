package zerobase.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.reservation.domain.AccountEntity;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    Optional<AccountEntity> findByEmailAuthKey(String emailAuthKey);

    Optional<AccountEntity> findByPhone(String phone);

}

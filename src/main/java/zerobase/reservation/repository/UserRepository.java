package zerobase.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.reservation.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
}

package itmo.deniill.dao.repository;

import itmo.deniill.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByProviderId(String providerId);

    Optional<User> findByEmail(String email);

    boolean existsByProviderId(String providerId);

    boolean existsByEmail(String email);
}

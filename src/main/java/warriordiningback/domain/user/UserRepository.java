package warriordiningback.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Page<User> findByEmailContaining(String email, Pageable pageable);

    Page<User> findByNameContaining(String name, Pageable pageable);

    Page<User> findByRolesContaining(Role role, Pageable pageable);

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndIsUsedTrue(String email);

    Optional<User> findByNameAndBirthAndPhoneAndIsUsedTrueAndFlagId(String name, String birth, String phone, Long flagId);

    @Override
    long count();
}

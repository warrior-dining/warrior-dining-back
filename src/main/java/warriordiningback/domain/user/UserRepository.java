package warriordiningback.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    /* 사용자 관리 검색 기능 */
    Page<User> findByEmailContaining(String email, Pageable pageable);
    Page<User> findByNameContaining(String name, Pageable pageable);
    Page<User> findByRolesContaining(Role role, Pageable pageable);

    boolean existsByEmail(String email);

    @Query("SELECT count(u) from User u WHERE u.createdAt > :date")
    Integer countRecentJoinUser(@Param("date") LocalDateTime date);

    @Override
    long count();
}

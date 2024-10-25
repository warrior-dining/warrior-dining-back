package warriordiningback.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface UserQueryRepository extends JpaRepository<User, Long> {

    @Query("SELECT count(u) from User u WHERE u.createdAt > :date")
    Integer countRecentJoinUser(@Param("date") LocalDateTime date);

    @Query("SELECT bm.id as placeId, bm.name as placeName, bm.addressNew, bm.phone, ROUND(COALESCE(AVG(r.rating), 0), 1) AS avgrating "
            + "FROM User u "
            + "JOIN u.bookmarks bm "
            + "LEFT JOIN bm.reviews r "
            + "WHERE u.email = :email "
            + "GROUP BY bm.id")
    Page<Object[]> findUserBookmarksWithAvgRating(@Param("email") String email, Pageable pageable);
}

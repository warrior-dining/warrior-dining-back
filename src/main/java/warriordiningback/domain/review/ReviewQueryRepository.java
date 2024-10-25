package warriordiningback.domain.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import warriordiningback.api.restaurant.dto.ReviewData;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewQueryRepository extends JpaRepository<Review, Long> {

    @Query("SELECT new warriordiningback.api.restaurant.dto.ReviewData(r.id, u.name, r.content, p.name) " +
            "FROM Review r " +
            "JOIN r.user u " +
            "JOIN r.place p " +
            "WHERE r.isDeleted = false " +
            "ORDER BY r.id DESC")
    List<ReviewData> findTop10ReviewData();

    @Query("SELECT r FROM Review r JOIN r.user u WHERE " +
            "u.name LIKE %:userName%")
    Page<Review> findAllByUserName(@Param("userName") String userName, Pageable pageable);

    @Query("SELECT r FROM Review r JOIN r.place p WHERE " +
            "p.name LIKE %:placeName%")
    Page<Review> findAllByPlaceName(@Param("placeName") String placeName, Pageable pageable);

    @Query("SELECT ROUND(AVG(r.rating), 1) FROM Review r WHERE r.createdAt > :date ")
    double avgRecentRating(@Param("date") LocalDateTime date);

    @Query("SELECT r FROM Review r JOIN r.user u WHERE u.id = :userId and r.isDeleted = false ORDER BY r.createdAt DESC")
    Page<Review> findByUserAndIsDeleted(@Param("userId") Long userId, Pageable pageable);
}


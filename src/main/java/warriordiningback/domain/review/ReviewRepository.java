package warriordiningback.domain.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import warriordiningback.api.restaurant.dto.ReviewData;
import warriordiningback.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT new warriordiningback.api.restaurant.dto.ReviewData(u.name, r.content) " +
            "FROM Review r JOIN r.user u " +
            "WHERE r.isDeleted = false")
    List<ReviewData> findAllReviewData();

    @Query("SELECT r FROM Review r JOIN r.user u WHERE "  +
            "u.name LIKE %:userName%")
    Page<Review> findAllByUserName(@Param("userName") String userName, Pageable pageable);

    @Query("SELECT r FROM Review r JOIN r.place p WHERE "  +
            "p.name LIKE %:placeName%")
    Page<Review> findAllByPlaceName(@Param("placeName") String placeName, Pageable pageable);

    Page<Review> findAllByContentContainingOrderByIdDesc(String content, Pageable pageable);

    Page<Review> findAllByRatingOrderByCreatedAtDesc(int rating, Pageable pageable);
    Page<Review> findAllByIsDeletedOrderByCreatedAtDesc(boolean isDeleted, Pageable pageable);
    Page<Review> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Review> findAllByOrderByCreatedAtAsc(Pageable pageable);

    @Query("SELECT ROUND(AVG(r.rating), 1) FROM Review r WHERE r.createdAt > :date ")
    double avgRecentRating(@Param("date") LocalDateTime date);

    @Override
    long count();
    
    Page<Review> findAllByUserOrderByCreatedAtDesc(User user ,Pageable pageable);
    
    @Query("SELECT r FROM Review r JOIN r.user u WHERE u.id = :userId and r.isDeleted = false")
    Page<Review> findByUserAndIsDeleted(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT r FROM Review r JOIN r.reservation re WHERE re.id = :reservationId")
    Review findByReservationId(@Param("reservationId") Long reservationId);

	boolean existsByReservationId(Long id);
}

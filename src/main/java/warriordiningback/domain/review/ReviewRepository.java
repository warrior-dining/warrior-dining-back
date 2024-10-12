package warriordiningback.domain.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import warriordiningback.api.restaurant.dto.ReviewData;
import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT new warriordiningback.api.restaurant.dto.ReviewData(u.name, r.content) " +
            "FROM Review r JOIN r.user u " +
            "WHERE r.isDeleted = false")
    List<ReviewData> findAllReviewData();

    @Query("SELECT r FROM Review r JOIN r.user u WHERE "  +
            "u.name LIKE %:userName%")
    Page<Review> findByUserName(@Param("userName") String userName, Pageable pageable);

    @Query("SELECT r FROM Review r JOIN r.place p WHERE "  +
            "p.name LIKE %:placeName%")
    Page<Review> findByPlaceName(@Param("placeName") String placeName, Pageable pageable);

    Page<Review> findAllByContentContainingOrderByIdDesc(String content, Pageable pageable);

    Page<Review> findAll(Pageable pageable);

}

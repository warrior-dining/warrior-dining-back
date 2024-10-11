package warriordiningback.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import warriordiningback.api.restaurant.dto.ReviewData;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT new warriordiningback.api.restaurant.dto.ReviewData(u.name, r.content) " +
            "FROM Review r JOIN r.user u " +
            "WHERE r.isDeleted = false")
    List<ReviewData> findAllReviewData();

}

package warriordiningback.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import warriordiningback.domain.review.Review;


public interface ReviewRepository extends JpaRepository<Review, Long> {

}

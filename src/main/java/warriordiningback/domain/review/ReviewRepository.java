package warriordiningback.domain.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByContentContainingOrderByIdDesc(String content, Pageable pageable);

    Page<Review> findAllByRatingOrderByCreatedAtDesc(int rating, Pageable pageable);

    Page<Review> findAllByIsDeletedOrderByCreatedAtDesc(boolean isDeleted, Pageable pageable);

    Page<Review> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Review> findAllByOrderByCreatedAtAsc(Pageable pageable);

    @Override
    long count();

    boolean existsByReservationId(Long id);
}

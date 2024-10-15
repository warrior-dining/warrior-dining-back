package warriordiningback.domain.place;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    <T> Page<T> findAllBy(Pageable pageable, Class<T> type); // 제네릭 메서드

    Page<Place> findAllByOrderByNameAsc(Pageable pageable);
    Page<Place> findByNameContainingOrderByNameAsc(String name, Pageable pageable);
    Page<Place> findByAddressNewContainingOrderByNameAsc(String addressNew, Pageable pageable);

    @Query("SELECT p FROM Place p ORDER BY p.createdAt LIMIT 5")
    List<Place> findTop5ByOrderByCreatedAtDESC();
}

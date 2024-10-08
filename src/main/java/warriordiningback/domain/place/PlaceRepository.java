package warriordiningback.domain.place;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    <T> Page<T> findAllBy(Pageable pageable, Class<T> type); // 제네릭 메서드



    Page<Place> findAllByOrderByNameAsc(Pageable pageable);
}

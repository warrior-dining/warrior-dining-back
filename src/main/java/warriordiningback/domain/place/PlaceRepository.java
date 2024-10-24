package warriordiningback.domain.place;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    <T> Page<T> findAllByOrderByNameAsc(Pageable pageable, Class<T> type); // 제네릭 메서드

    <T> Page<T> findAllById(Long id, Pageable pageable, Class<T> type);

    <T> Page<T> findAllByCodeId(Long codeId, Pageable pageable, Class<T> type);

    Page<Place> findAllByOrderByNameAsc(Pageable pageable);

    Page<Place> findByNameContainingOrderByNameAsc(String name, Pageable pageable);

    Page<Place> findByAddressNewContainingOrderByNameAsc(String addressNew, Pageable pageable);

}

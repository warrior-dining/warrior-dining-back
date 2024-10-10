package warriordiningback.domain.place;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Page<Place> findAllByOrderByNameAsc(Pageable pageable);
}

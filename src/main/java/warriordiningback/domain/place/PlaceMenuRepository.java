package warriordiningback.domain.place;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceMenuRepository extends JpaRepository<PlaceMenu, Long> {

    List<PlaceMenu> findByPlaceId(Long placeId);

}

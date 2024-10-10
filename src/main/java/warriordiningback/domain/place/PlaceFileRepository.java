package warriordiningback.domain.place;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceFileRepository extends JpaRepository<PlaceFile, Long> {

    List<PlaceFile> findByPlaceId(Long id);
}

package warriordiningback.domain.place;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceFileRepository extends JpaRepository<PlaceFile, Long> {

    List<PlaceFile> findByPlaceId(Long id);
    Optional<PlaceFile> findBySavePath(String savePath);
}

package warriordiningback.domain.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import warriordiningback.domain.reservation.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

	Page<Reservation> findAllByOrderByIdDesc(Pageable pageable);
	
}

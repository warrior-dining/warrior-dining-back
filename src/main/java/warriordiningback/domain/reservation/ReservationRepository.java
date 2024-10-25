package warriordiningback.domain.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import warriordiningback.domain.Code;
import warriordiningback.domain.user.User;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    Page<Reservation> findById(Long id, Pageable pageable);

    Page<Reservation> findAllByCodeOrderByCreatedAtDesc(Pageable pageable, Code code);

    Page<Reservation> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Reservation> findAllByUserOrderByReservationDateDesc(User user, Pageable pageable);

    long count();

}

package warriordiningback.domain.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import warriordiningback.domain.Code;
import warriordiningback.domain.user.User;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findById(Long id, Pageable pageable);

    Page<Reservation> findAllByCodeOrderByCreatedAtDesc(Pageable pageable, Code code);

    @Query("SELECT r FROM Reservation r JOIN r.place p JOIN p.user u JOIN r.code c WHERE " +
            "u.id = :userId AND c.id = :status ORDER BY r.reservationDate DESC")
    Page<Reservation> findAllByOwner(@Param("userId") Long userId, @Param("status") Long status, Pageable pageable);

    @Query("SELECT r FROM Reservation r JOIN r.place p JOIN p.user u WHERE " +
            "u.id = :userId ORDER BY r.reservationDate DESC")
    Page<Reservation> findAllByOwnerNotStatus(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM Reservation r JOIN r.user u WHERE " +
            "u.name LIKE %:userName%")
    Page<Reservation> findAllByUserName(@Param("userName") String userName, Pageable pageable);

    Page<Reservation> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Reservation> findAllByUserOrderByReservationDateDesc(User user, Pageable pageable);

    long count();

}

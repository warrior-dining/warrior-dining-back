package warriordiningback.domain.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import warriordiningback.api.restaurant.dto.MonthReservationData;
import warriordiningback.api.restaurant.dto.TopReservationData;
import warriordiningback.domain.Code;
import warriordiningback.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT new warriordiningback.api.restaurant.dto.TopReservationData(p.id, p.name, p.comment, " +
            "(SELECT MIN(pf.url) FROM p.placeFiles pf), p.code.id) " +
            "FROM Reservation r " +
            "JOIN r.place p " +
            "GROUP BY p.id, p.code.id " +
            "ORDER BY COUNT(r) DESC")
    List<TopReservationData> findTop10Places();


    @Query("SELECT new warriordiningback.api.restaurant.dto.MonthReservationData(p.id, p.name, p.comment, " +
            "(SELECT MIN(pf.url) FROM p.placeFiles pf), r.reservationDate) " +
            "FROM Reservation r " +
            "JOIN r.place p " +
            "WHERE FUNCTION('YEAR', r.reservationDate) = :year AND FUNCTION('MONTH', r.reservationDate) = :month " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(r) DESC")
    List<MonthReservationData> findMonthPlaces(@Param("year") Integer year, @Param("month") Integer month);


    Page<Reservation> findAllByOrderByIdDesc(Pageable pageable);

    Page<Reservation> findById(Long id, Pageable pageable);

    Page<Reservation> findAllByCodeOrderByCreatedAtDesc(Pageable pageable, Code code);

    @Query("SELECT r FROM Reservation r JOIN r.user u WHERE " +
            "u.name LIKE %:userName%")
    Page<Reservation> findAllByUserName(@Param("userName") String userName, Pageable pageable);

    Page<Reservation> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.createdAt > :date ")
    Integer countRecentReservations(@Param("date") LocalDateTime date);

    long count();

    @Query("SELECT r FROM Reservation r JOIN r.place p WHERE " +
            "p.name LIKE %:placeName%")
    Page<Reservation> findAllByPlaceName(@Param("placeName") String placeName, Pageable pageable);

    Page<Reservation> findAllByUserOrderByReservationDateDesc(User user, Pageable pageable);


}

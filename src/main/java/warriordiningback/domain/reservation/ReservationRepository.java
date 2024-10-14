package warriordiningback.domain.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import warriordiningback.api.restaurant.dto.MonthReservationData;
import warriordiningback.api.restaurant.dto.TopReservationData;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT new warriordiningback.api.restaurant.dto.TopReservationData(p.name, p.comment, " +
            "(SELECT MIN(pf.url) FROM p.placeFiles pf)) " +
            "FROM Reservation r " +
            "JOIN r.place p " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(r) DESC")
    List<TopReservationData> findTop10Places();


    @Query("SELECT new warriordiningback.api.restaurant.dto.MonthReservationData(p.name, p.comment, " +
            "(SELECT MIN(pf.url) FROM p.placeFiles pf), r.reservationDate) " +
            "FROM Reservation r " +
            "JOIN r.place p " +
            "WHERE FUNCTION('YEAR', r.reservationDate) = :year AND FUNCTION('MONTH', r.reservationDate) = :month " +
            "GROUP BY p.id, r.reservationDate " +
            "ORDER BY COUNT(r) DESC")
    List<MonthReservationData> findMonthPlaces(@Param("year") Integer year, @Param("month") Integer month);

    Page<Reservation> findAllByOrderByIdDesc(Pageable pageable);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.createdAt > :date ")
    Integer countRecentReservations(@Param("date") LocalDateTime date);

    long count();

}

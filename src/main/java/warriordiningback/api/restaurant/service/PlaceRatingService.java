package warriordiningback.api.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import warriordiningback.api.restaurant.dto.MonthReservationData;
import warriordiningback.api.restaurant.dto.TopReservationData;
import warriordiningback.domain.reservation.ReservationQueryRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceRatingService {

    @Autowired
    private ReservationQueryRepository reservationQueryRepository;

    public List<TopReservationData> findTop10Places() {
        return reservationQueryRepository.findTop10Places();
    }

    public List<MonthReservationData> findMonthPlaces(Integer year, Integer month) {
        LocalDate now = LocalDate.now();
        if (year == null) {
            year = now.getYear();
        }
        if (month == null) {
            month = now.getMonthValue();
        }
        if (year <= 0 || month < 1 || month > 12) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid year or month.");
        }

        return reservationQueryRepository.findMonthPlaces(year, month);
    }

}

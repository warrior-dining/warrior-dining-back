package warriordiningback.api.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import warriordiningback.api.restaurant.dto.MonthReservationData;
import warriordiningback.api.restaurant.dto.ReviewData;
import warriordiningback.api.restaurant.dto.TopReservationData;
import warriordiningback.domain.reservation.ReservationRepository;
import warriordiningback.domain.review.ReviewRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<TopReservationData> findTop10Places() {
        return reservationRepository.findTop10Places();
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

        return reservationRepository.findMonthPlaces(year, month);
    }

    public List<ReviewData> findByReview() {
        return reviewRepository.findAllReviewData();
    }


}

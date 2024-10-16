package warriordiningback.api.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warriordiningback.api.user.dto.UserReservationListResponse;
import warriordiningback.domain.reservation.Reservation;
import warriordiningback.domain.reservation.ReservationRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class UserReservationServiceImp implements UserReservationService{

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, Object> myReservationList(String email, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", false);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));
        Page<Reservation> myReservations = reservationRepository.findAllByUserOrderByReservationDateDesc(user, pageable);
        List<UserReservationListResponse> userReservationListResponse = new ArrayList<>();

        for (Reservation row : myReservations.getContent()) {
            UserReservationListResponse myReservation = new UserReservationListResponse(row);
            userReservationListResponse.add(myReservation);
        }
        Page<UserReservationListResponse> results = new PageImpl<>(userReservationListResponse, myReservations.getPageable(), myReservations.getTotalElements());

        resultMap.put("status", true);
        resultMap.put("results", results);
        return resultMap;
    }

    void userCheck(String email){
        if(!userRepository.existsByEmail(email)){
            throw new DiningApplicationException(ErrorCode.USER_NOT_FOUND);
        }
    }
}

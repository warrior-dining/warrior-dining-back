package warriordiningback.api.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warriordiningback.domain.reservation.Reservation;
import warriordiningback.domain.reservation.ReservationRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

import java.util.HashMap;
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
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));

        Page<Reservation> myReservations;
        myReservations = reservationRepository.findAllByUserOrderByReservationDateDesc(user, pageable);

        resultMap.put("status", true);
        resultMap.put("results", myReservations);
        return resultMap;
    }

    void userCheck(String email){
        if(!userRepository.existsByEmail(email)){
            throw new DiningApplicationException(ErrorCode.USER_NOT_FOUND);
        }
    }
}

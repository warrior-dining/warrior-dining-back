package warriordiningback.api.user.service;

import org.springframework.data.domain.Pageable;
import warriordiningback.api.user.dto.UserReservationAddRequest;

import java.util.Map;

public interface UserReservationService {

    public Map<String, Object> myReservationList(String email, Pageable pageable);
    public Map<String, Object> reservationAdd(UserReservationAddRequest reqData);
    public Map<String, Object> myReservationInfo(Long reservationId);
}

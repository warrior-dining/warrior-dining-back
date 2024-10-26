package warriordiningback.api.user.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import warriordiningback.api.user.dto.UserReservationAddRequest;
import warriordiningback.api.user.dto.UserReservationEditRequest;

import java.util.Map;

public interface UserReservationService {

    public Map<String, Object> myReservationList(UserDetails userDetails, Pageable pageable);
    public Map<String, Object> reservationAdd(UserDetails userDetails, UserReservationAddRequest reqData);
    public Map<String, Object> myReservationInfo(Long reservationId);
    public Map<String, Object> myreservationEdit(Long reservationId, UserReservationEditRequest reqData);
    public Map<String, Object> myReservationDelete(Long reservationId);
}

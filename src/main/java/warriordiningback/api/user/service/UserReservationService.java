package warriordiningback.api.user.service;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface UserReservationService {

    public Map<String, Object> myReservationList(String email, Pageable pageable);
}

package warriordiningback.api.owner.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface OwnerReservationService {

    public Map<String, Object> ownerMain(String status, UserDetails userDetails, Pageable pageable);
}

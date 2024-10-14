package warriordiningback.api.admin.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

public interface AdminReservationService {
	
	public Map<String, Object> reservationList(String searchType,String searchKeyword,String status, Pageable pageable);
	public Map<String, Object> updateReservation(Long id, Map<String, Long> statusMap);

}

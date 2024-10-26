package warriordiningback.api.admin.service;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AdminReservationService {

    Map<String, Object> reservationList(String searchType, String searchKeyword, String status, Pageable pageable);

    Map<String, Object> updateReservation(Long id, Map<String, Long> statusMap);

}

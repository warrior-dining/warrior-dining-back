package warriordiningback.api.admin.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import warriordiningback.domain.Code;
import warriordiningback.domain.CodeRepository;
import warriordiningback.domain.reservation.Reservation;
import warriordiningback.domain.reservation.ReservationRepository;

@Service
@Transactional(readOnly = true)
public class AdminReservationServiceImp implements AdminReservationService{
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private CodeRepository codeRepository;

	@Override
	public Map<String, Object> reservationList(String searchType, String searchKeyword, String status, Pageable pageable) {
		Map<String, Object> resultMap = new HashMap<>();
		Page<Reservation> searchReservation;
		if(status != null && !status.isEmpty()) {
			Code code = codeRepository.findById(Long.parseLong(status)).orElseThrow(()-> new RuntimeException("해당코드가 존재하지 않음"));
			searchReservation = reservationRepository.findAllByCodeOrderByCreatedAtDesc(pageable, code);
		} else {
			searchReservation = reservationRepository.findAllByOrderByIdDesc(pageable);
		}
		resultMap.put("data",searchReservation);
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> updateReservation(Long id, Map<String, Long> statusMap) {
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("status", false);
		
		Code code = null;
		if(statusMap.get("status") != null && statusMap.get("status") == 14) {
			code = codeRepository.findById(13L).orElseThrow(()-> new RuntimeException());
		} else {
			responseMap.put("message", "잘못 요청한듯");
			return responseMap;
		}
		
		if(reservationRepository.existsById(id)) {
			Reservation reservations = reservationRepository.findById(id).orElseThrow(()-> new RuntimeException("해당아이디 없음"));
			reservations.updateStatus(code);
			responseMap.put("status", true);
			responseMap.put("results", reservations);
		}
		return responseMap;
	}

	
	
}

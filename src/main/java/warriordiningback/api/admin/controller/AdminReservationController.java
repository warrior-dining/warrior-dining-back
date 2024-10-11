package warriordiningback.api.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import warriordiningback.domain.Code;
import warriordiningback.domain.reservation.Reservation;
import warriordiningback.domain.CodeRepository;
import warriordiningback.domain.reservation.ReservationRepository;

@RestController
@RequestMapping("/api/admin/reservations")
public class AdminReservationController {

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired CodeRepository codeRepository;
	
	@GetMapping("/")
	public Map<String, Object> map(Pageable pageable) {
		Map<String, Object> map = new HashMap<>();
		map.put("data", reservationRepository.findAllByOrderByIdDesc(pageable));
		return map;
	}
	
	@PatchMapping("/{id:[0-9]+}")
	public Map<String, Object> updateReservation(@PathVariable("id") Long id,
												 @RequestBody Map<String, Long> statusMap){
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
			Reservation updateReservation = reservations.updateStatus(
					id, reservations.getReservationDate(), reservations.getReservationTime(), reservations.getCount(), 
		            reservations.getOrderNote(), reservations.getUser(), reservations.getPlace(), code);
			updateReservation = reservationRepository.save(updateReservation);
			responseMap.put("status", true);
			responseMap.put("results", updateReservation);
		}
		return responseMap;
	}
}

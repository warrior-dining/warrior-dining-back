package warriordiningback.api.admin.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import warriordiningback.api.admin.service.AdminReservationService;

@RestController
@RequestMapping("/api/admin/reservations")
public class AdminReservationController {

	@Autowired
	private AdminReservationService adminReservationService;
	
	@GetMapping("/")
	public Map<String, Object> map(@RequestParam(name = "type", required = false) String searchType,
			  					   @RequestParam(name = "keyword", required = false) String searchKeyword,
			  					   @RequestParam(name = "status", required = false) String status, Pageable pageable) {
		
		return adminReservationService.reservationList(searchType, searchKeyword, status, pageable);
	}
	
	@PatchMapping("/{id:[0-9]+}")
	public Map<String, Object> updateReservation(@PathVariable("id") Long id,
												 @RequestBody Map<String, Long> statusMap){
		return adminReservationService.updateReservation(id, statusMap);
	}
}

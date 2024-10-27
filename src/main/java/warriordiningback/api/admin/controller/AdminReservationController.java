package warriordiningback.api.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import warriordiningback.api.admin.service.AdminReservationService;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/reservations")
@RequiredArgsConstructor
public class AdminReservationController {

    private final AdminReservationService adminReservationService;

    @GetMapping
    public Map<String, Object> map(@RequestParam(name = "type", required = false) String searchType,
                                   @RequestParam(name = "keyword", required = false) String searchKeyword,
                                   @RequestParam(name = "status", required = false) String status, Pageable pageable) {

        return adminReservationService.reservationList(searchType, searchKeyword, status, pageable);
    }

    @PatchMapping("/{id:[0-9]+}")
    public Map<String, Object> updateReservation(@PathVariable("id") Long id,
                                                 @RequestBody Map<String, Long> statusMap) {

        return adminReservationService.updateReservation(id, statusMap);
    }
}

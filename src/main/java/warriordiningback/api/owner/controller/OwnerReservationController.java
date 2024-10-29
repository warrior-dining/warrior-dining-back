package warriordiningback.api.owner.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import warriordiningback.api.owner.service.OwnerReservationService;

import java.util.Map;

@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerReservationController {

    private final OwnerReservationService ownerReservationService;

    @GetMapping
    public Map<String, Object> ownerMain(@RequestParam(name = "status", required = false) String status,
                                         @AuthenticationPrincipal UserDetails userDetails, Pageable pageable) {
        return ownerReservationService.ownerMain(status, userDetails, pageable);
    }

    @PatchMapping("/{id:[0-9]+}")
    public Map<String, Object> updateReservation(@PathVariable("id") Long id,
                                                 @RequestBody Map<String, Long> statusMap) {

        return ownerReservationService.updateReservation(id, statusMap);
    }
}

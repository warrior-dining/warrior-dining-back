package warriordiningback.api.owner.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import warriordiningback.api.owner.service.OwnerReservationService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerReservationController {

    private final OwnerReservationService ownerReservationService;

    @GetMapping
    public Map<String, Object> ownerMain(@RequestParam(name = "status", required = false) String status,
                                         @AuthenticationPrincipal UserDetails userDetails, Pageable pageable) {
        log.info("호출됨 !");
        return ownerReservationService.ownerMain(status, userDetails, pageable);
    }
}

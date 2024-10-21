package warriordiningback.api.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import warriordiningback.api.user.dto.UserReservationAddRequest;
import warriordiningback.api.user.service.UserReservationService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/member/reservation")
public class UserReservationController {

    @Autowired
    private UserReservationService userReservationService;

    @GetMapping("/")
    public Map<String, Object> myReservationList(@RequestParam("email") String email, Pageable pageable){
        return userReservationService.myReservationList(email, pageable);
    }

    @PostMapping("/")
    public Map<String, Object> reservationAdd(@RequestBody UserReservationAddRequest reqData){
        log.info("reqData : {}", reqData.toString());
        return userReservationService.reservationAdd(reqData);
    }
}

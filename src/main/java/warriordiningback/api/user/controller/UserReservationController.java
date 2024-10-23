package warriordiningback.api.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import warriordiningback.api.user.dto.UserReservationAddRequest;
import warriordiningback.api.user.dto.UserReservationEditRequest;
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
        return userReservationService.reservationAdd(reqData);
    }

    @GetMapping("/{reservationId:[0-9]+}")
    public Map<String, Object> myReservationInfo(@PathVariable("reservationId") Long reservationId){
        return userReservationService.myReservationInfo(reservationId);
    }

    @PutMapping("/{reservationId:[0-9]+}")
    public Map<String, Object> myReservationEdit(@PathVariable("reservationId") Long reservationId, @RequestBody UserReservationEditRequest reqData){
        return userReservationService.myreservationEdit(reservationId, reqData);
    }

    @DeleteMapping("/{reservationId:[0-9]+}")
    public Map<String, Object> myReservationDelete(@PathVariable("reservationId") Long reservationId){
        return userReservationService.myReservationDelete(reservationId);
    }
}

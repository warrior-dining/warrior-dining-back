package warriordiningback.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import warriordiningback.api.user.service.UserReservationService;

import java.util.Map;

@RestController
@RequestMapping("/api/member/reservation")
public class UserReservationController {

    @Autowired
    private UserReservationService userReservationService;

    @GetMapping("/")
    public Map<String, Object> myReservationList(@RequestParam("email") String email, Pageable pageable){
        return userReservationService.myReservationList(email, pageable);
    }

}

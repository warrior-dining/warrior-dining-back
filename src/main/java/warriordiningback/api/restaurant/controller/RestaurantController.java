package warriordiningback.api.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import warriordiningback.domain.place.PlaceInfo;
import warriordiningback.domain.place.PlaceRepository;


@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    @Autowired
    private PlaceRepository placeRepository;

    @GetMapping
    public Page<PlaceInfo> restaurantList(@PageableDefault(size = 10) Pageable pageable){
        return placeRepository.findAllBy(pageable, PlaceInfo.class);
    }


}

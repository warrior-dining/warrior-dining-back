package warriordiningback.api.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warriordiningback.api.restaurant.service.RestaurantService;
import warriordiningback.domain.place.PlaceInfo;
import warriordiningback.domain.place.PlaceRepository;


@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public Page<PlaceInfo> restaurantList(@PageableDefault(size = 10) Pageable pageable) {
        return restaurantService.restaurantList(pageable);
    }


}

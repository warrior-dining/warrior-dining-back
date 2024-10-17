package warriordiningback.api.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import warriordiningback.api.restaurant.dto.MonthReservationData;
import warriordiningback.api.restaurant.dto.PlaceSearchData;
import warriordiningback.api.restaurant.dto.ReviewData;
import warriordiningback.api.restaurant.dto.TopReservationData;
import warriordiningback.api.restaurant.service.PlaceSearchService;
import warriordiningback.api.restaurant.service.RestaurantService;
import warriordiningback.domain.place.PlaceDetailInfo;
import warriordiningback.domain.place.PlaceInfo;
import warriordiningback.domain.place.PlaceRepository;

import java.util.List;


@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private PlaceSearchService placeSearchService;

    @GetMapping
    public Page<PlaceInfo> restaurantList(@PageableDefault(size = 10) Pageable pageable) {
        return placeRepository.findAllBy(pageable, PlaceInfo.class);
    }

    @GetMapping("/top")
    public List<TopReservationData> getTopReservations() {
        return restaurantService.findTop10Places();
    }

    @GetMapping("/month")
    public List<MonthReservationData> getMonthReservations(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month) {

        return restaurantService.findMonthPlaces(year, month);
    }

    @GetMapping("reviews")
    public List<ReviewData> restaurantReviews() {
        return restaurantService.findByReview();
    }

    @GetMapping("/{id}")
    public Page<PlaceDetailInfo> restaurantDetail(
            @PathVariable("id") Long id,
            @PageableDefault(size = 3) Pageable pageable) {
        return placeRepository.findAllById(id, pageable, PlaceDetailInfo.class);
    }

    @GetMapping("/search")
    public Page<PlaceSearchData> searchPlaces(
            @RequestParam String keyword,
            @PageableDefault(size = 10) Pageable pageable) {
        return placeSearchService.searchPlaces(keyword, pageable);
    }

}

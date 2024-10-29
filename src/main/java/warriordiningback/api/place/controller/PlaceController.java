package warriordiningback.api.place.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import warriordiningback.api.place.docs.PlaceControllerDocs;
import warriordiningback.api.place.dto.MonthReservationData;
import warriordiningback.api.place.dto.PlaceSearchData;
import warriordiningback.api.place.dto.ReviewData;
import warriordiningback.api.place.dto.TopReservationData;
import warriordiningback.api.place.service.PlaceFilterService;
import warriordiningback.api.place.service.PlaceRatingService;
import warriordiningback.api.place.service.PlaceReviewService;
import warriordiningback.api.place.service.PlaceSearchService;
import warriordiningback.domain.place.PlaceDetailInfo;
import warriordiningback.domain.place.PlaceInfo;
import warriordiningback.domain.place.PlaceRepository;

import java.util.List;


@RestController
@RequestMapping("/api/place")
@RequiredArgsConstructor
public class PlaceController implements PlaceControllerDocs {

    private final PlaceRepository placeRepository;
    private final PlaceRatingService placeRatingService;
    private final PlaceSearchService placeSearchService;
    private final PlaceFilterService placeFilterService;
    private final PlaceReviewService placeReviewService;

    @GetMapping
    public Page<PlaceInfo> placeList(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @PageableDefault(size = 10) Pageable pageable) {
        return placeFilterService.filterPlaces(categoryId, minPrice, maxPrice, pageable);
    }

    @GetMapping("/top")
    public List<TopReservationData> getTopReservations() {
        return placeRatingService.findTop10Places();
    }

    @GetMapping("/month")
    public List<MonthReservationData> getMonthReservations(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month) {

        return placeRatingService.findMonthPlaces(year, month);
    }

    @GetMapping("reviews")
    public List<ReviewData> placeReviews() {
        return placeReviewService.findByReview();
    }

    @GetMapping("/{id}")
    public Page<PlaceDetailInfo> placeDetail(
            @PathVariable("id") Long id,
            @PageableDefault(size = 3) Pageable pageable) {
        return placeRepository.findAllById(id, pageable, PlaceDetailInfo.class);
    }

    @GetMapping("/search")
    public Page<PlaceSearchData> searchPlaces(
            @RequestParam String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @PageableDefault(size = 10) Pageable pageable) {
        return placeSearchService.searchPlaces(keyword, categoryId, minPrice, maxPrice, pageable);
    }

}

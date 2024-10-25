package warriordiningback.api.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import warriordiningback.domain.place.Place;
import warriordiningback.domain.place.PlaceInfo;
import warriordiningback.domain.place.PlaceQueryRepository;
import warriordiningback.domain.place.PlaceRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class PlaceFilterService {

    private final PlaceRepository placeRepository;
    private final PlaceQueryRepository placeQueryRepository;

    public Page<PlaceInfo> filterPlaces(Long categoryId, Long minPrice, Long maxPrice, Pageable pageable) {
        // 카테고리와 가격대 모두 필터링할 경우
        if (categoryId != null && minPrice != null && maxPrice != null) {
            return placeQueryRepository.findAllByCategoryAndPriceRange(categoryId, minPrice, maxPrice, pageable, PlaceInfo.class);
        }
        // 카테고리만 필터링할 경우
        else if (categoryId != null) {
            return placeRepository.findAllByCodeId(categoryId, pageable, PlaceInfo.class);
        }
        // 가격대만 필터링할 경우
        else if (minPrice != null && maxPrice != null) {
            return placeQueryRepository.findAllByPriceRange(minPrice, maxPrice, pageable, PlaceInfo.class);
        }
        // 필터 조건이 없을 경우 전체 데이터 반환
        else {
            return placeRepository.findAllByOrderByNameAsc(pageable, PlaceInfo.class);
        }
    }

    public Place findPlaceById(Long id) {
        return placeRepository.findById(id).orElseThrow(()
                -> new DiningApplicationException(ErrorCode.PLACE_NOT_FOUND));
    }
}

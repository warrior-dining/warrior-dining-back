package warriordiningback.api.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import warriordiningback.api.restaurant.dto.PlaceSearchData;
import warriordiningback.domain.place.Place;
import warriordiningback.domain.place.PlaceFile;
import warriordiningback.domain.place.PlaceMenu;
import warriordiningback.domain.place.PlaceQueryRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceSearchService {

    private final PlaceQueryRepository placeRepository;

    public Page<PlaceSearchData> searchPlaces(String keyword, Long categoryId, Double minPrice, Double maxPrice, Pageable pageable) {
        Page<Place> places = placeRepository.findAllByNameOrPlaceMenuOrAddressNew(keyword, categoryId, minPrice, maxPrice, pageable);

        return places.map(place -> new PlaceSearchData(
                place.getId(),
                place.getName(),
                place.getComment(),
                place.getAddressNew(),
                place.getPlaceMenus().stream().map(PlaceMenu::getMenu)
                        .collect(Collectors.joining(", ")),
                place.getPlaceFiles().stream().map(PlaceFile::getUrl)
                        .collect(Collectors.toList()),
                place.getCode().getId(),
                null, // 가격은 필요에 따라 설정
                null  // 가격은 필요에 따라 설정
        ));
    }

}

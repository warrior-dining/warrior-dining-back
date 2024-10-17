package warriordiningback.api.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import warriordiningback.api.restaurant.dto.PlaceSearchDto;
import warriordiningback.domain.place.Place;
import warriordiningback.domain.place.PlaceFile;
import warriordiningback.domain.place.PlaceMenu;
import warriordiningback.domain.place.PlaceRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceSearchService {

    private final PlaceRepository placeRepository;

    public Page<PlaceSearchDto> searchPlaces(String keyword, Pageable pageable) {
        Page<Place> places = placeRepository.findAllByNameOrPlaceMenuOrAddressNew(keyword, pageable);

        // Page 객체 자체에서 map()을 사용하여 Place -> PlaceSearchDto 변환
        return places.map(place -> new PlaceSearchDto(
                place.getId(),
                place.getName(),
                place.getComment(),
                place.getAddressNew(),
                place.getPlaceMenus().stream().map(PlaceMenu::getMenu)  // 메뉴 이름을 가져옴
                        .collect(Collectors.joining(", ")), // 여러 메뉴 이름을 ','로 연결
                place.getPlaceFiles().stream().map(PlaceFile::getUrl)
                        .collect(Collectors.toList())

        ));
    }

}

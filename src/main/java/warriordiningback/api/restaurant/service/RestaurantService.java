package warriordiningback.api.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import warriordiningback.domain.place.PlaceInfo;
import warriordiningback.domain.place.PlaceRepository;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    @Autowired
    private PlaceRepository placeRepository;

    public Page<PlaceInfo> restaurantList(Pageable pageable) {
        return placeRepository.findAllBy(pageable, PlaceInfo.class);
    }


}

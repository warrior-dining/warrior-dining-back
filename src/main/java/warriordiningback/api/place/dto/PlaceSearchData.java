package warriordiningback.api.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PlaceSearchData {

    private Long id;
    private String name;
    private String comment;
    private String addressNew;
    private String menu;
    private List<String> url;
    private Long categoryId;
    private Double minPrice;
    private Double maxPrice;
}

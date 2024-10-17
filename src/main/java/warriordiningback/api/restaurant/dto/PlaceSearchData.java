package warriordiningback.api.restaurant.dto;

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
}

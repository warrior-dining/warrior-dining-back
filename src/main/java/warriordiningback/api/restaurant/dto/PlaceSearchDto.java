package warriordiningback.api.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PlaceSearchDto {

    private Long id;
    private String name;
    private String comment;
    private String addressNew;
    private String menu;
    private List<String> url;

}

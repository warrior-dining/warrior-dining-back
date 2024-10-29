package warriordiningback.api.place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewData {

    private Long id;
    private String name;
    private String content;
    private String placeName;

}

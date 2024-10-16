package warriordiningback.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookMarkResponse {

    private Long placeId;
    private String placeName;
    private String addressNew;
    private String phone;
    private Double avgRating;

}

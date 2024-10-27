package warriordiningback.api.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import warriordiningback.domain.place.Place;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class PlaceRecentResponse {

    private String createdAt;
    private String placeName;

    public PlaceRecentResponse(Place place) {
        this.createdAt = formatReservationDate(place.getCreatedAt());
        this.placeName = placeName;
    }

    private String formatReservationDate(LocalDateTime date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}

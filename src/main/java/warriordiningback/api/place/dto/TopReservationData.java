package warriordiningback.api.place.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopReservationData {

    private Long id;
    private String name;
    private String comment;
    private String url;
    private Long value;

}

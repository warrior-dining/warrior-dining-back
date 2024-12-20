package warriordiningback.api.place.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@AllArgsConstructor
public class MonthReservationData {

    private Long id;
    private String name;
    private String comment;
    private String url;
    private String reservationDate;
    private Long value;

    public MonthReservationData(Long id, String name, String comment, String url, Date reservationDate, Long value) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.url = url;
        this.value = value;
        this.reservationDate = reservationDate.toInstant()
                .atZone(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}


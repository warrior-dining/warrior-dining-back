package warriordiningback.api.restaurant.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@AllArgsConstructor
public class MonthReservationData {
    private String name;
    private String comment;
    private String url;
    private String reservationDate;

    public MonthReservationData(String name, String comment, String url, Date reservationDate) {
        this.name = name;
        this.comment = comment;
        this.url = url;
        this.reservationDate = reservationDate.toInstant()
                .atZone(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}


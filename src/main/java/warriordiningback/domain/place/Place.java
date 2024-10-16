package warriordiningback.domain.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import warriordiningback.domain.BaseEntity;
import warriordiningback.domain.Code;
import warriordiningback.domain.review.Review;
import warriordiningback.domain.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Table(name = "places")
public class Place extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String addressOld;

    private String addressNew;

    private String phone;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "off_day")
    private String offDay;

    private String comment;

    @OneToMany(mappedBy = "place")
    private final List<PlaceMenu> placeMenus = new ArrayList<>();
    //
    @OneToMany(mappedBy = "place")
    private final List<PlaceFile> placeFiles = new ArrayList<>();

    //    @OneToMany(mappedBy = "place")
//    private List<Reservation> reservations = new ArrayList<>();
//
    @OneToMany(mappedBy = "place")
    @JsonIgnore
    private final List<Review> reviews = new ArrayList<>();

    //(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "code_id", nullable = false)
    private Code code;

    // 음식점 정보 엔티티 시간타입 알아볼 것!
    /* 비즈니스 로직 */
    public static Place create(String name,
                               User user,
                               Code category,
                               String addressNew,
                               String phone,
                               String startTime,
                               String endTime,
                               String offDay,
                               String comment) {
        Place place = new Place();
        place.name = name;
        place.user = user;
        place.code = category;
        place.addressNew = addressNew;
        place.phone = phone;
        place.startTime = startTime;
        place.endTime = endTime;
        place.offDay = offDay;
        place.comment = comment;
        return place;
    }

    public void update(String name,
                       User user,
                       Code category,
                       String addressNew,
                       String phone,
                       String startTime,
                       String endTime,
                       String offDay,
                       String comment) {
        this.name = name;
        this.user = user;
        this.code = category;
        this.addressNew = addressNew;
        this.phone = phone;
        this.startTime = startTime;
        this.endTime = endTime;
        this.offDay = offDay;
        this.comment = comment;
    }

}

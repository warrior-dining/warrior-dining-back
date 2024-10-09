package warriordiningback.domain.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "place_menu")
public class PlaceMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    @JsonIgnore
    private Place place;

    @Column(nullable = false)
    private String menu;

    @Column(nullable = false)
    private int price;

    @Column(name = "view_order")
    private int viewOrder;

    public static PlaceMenu create( Place place, String menu, int price) {
        PlaceMenu placeMenu = new PlaceMenu();
        placeMenu.place = place;
        placeMenu.menu = menu;
        placeMenu.price = price;
        return placeMenu;
    }

    public void update(String menu, int price) {
        this.menu = menu;
        this.price = price;
    }
}

package warriordiningback.domain.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "place_file")
public class PlaceFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    @JsonIgnore
    private Place place;

    private String name; // 원본 파일명

    private String extension; // 파일 확장자

    private String url; // url

    @Column(name = "save_name")
    private String saveName; // 저장시 변경된 파일명

    @Column(name = "save_path")
    private String savePath; // 저장 경로

    @Column(name = "media_type")
    private String mediaType; // 미디어 타입

    @Column(name = "view_order")
    private int viewOrder;

    public static PlaceFile create(Place place, String name, String extension, String url, String saveName, String savePath, String mediaType, int viewOrder) {
        PlaceFile placeFile = new PlaceFile();
        placeFile.place = place;
        placeFile.name = name;
        placeFile.extension = extension;
        placeFile.url = url;
        placeFile.saveName = saveName;
        placeFile.savePath = savePath;
        placeFile.mediaType = mediaType;
        placeFile.viewOrder = viewOrder;
        return placeFile;
    }

}

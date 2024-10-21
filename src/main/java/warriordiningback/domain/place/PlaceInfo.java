package warriordiningback.domain.place;

import java.util.List;

public interface PlaceInfo {

    Long getId();

    String getName();

    String getComment();

    List<PlaceFileInfo> getPlaceFiles();

    CodeInfo getCode();

    List<PlaceMenuInfo> getPlaceMenus();

    interface PlaceFileInfo {
        String getUrl();
    }

    interface CodeInfo {
        Long getId();
    }

    interface PlaceMenuInfo {
        Long getPrice();
    }

}

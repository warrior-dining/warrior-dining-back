package warriordiningback.domain.place;

import java.util.List;

public interface PlaceInfo {

    Long getId();

    String getName();

    String getComment();

    List<PlaceFileInfo> getPlaceFiles();

    interface PlaceFileInfo {
        String getUrl();

    }

}

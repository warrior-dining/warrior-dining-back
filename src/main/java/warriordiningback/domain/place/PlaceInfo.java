package warriordiningback.domain.place;

import java.util.List;

public interface PlaceInfo {

    String getName();

    String getComment();

    List<PlaceFileInfo> getPlaceFiles();

    interface PlaceFileInfo {
        String getUrl();

    }

}

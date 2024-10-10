package warriordiningback.domain.place;

public interface PlaceInfo {

    String getName();

    String getComment();

    PlaceFileInfo getPlaceFiles();

    interface PlaceFileInfo {
        String getUrl();
    }

}

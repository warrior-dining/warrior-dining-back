package warriordiningback.domain.place;

import java.util.List;

public interface PlaceDetailInfo {

    Long getId();

    String getName();

    String getComment();

    String getStartTime();

    String getEndTime();

    String getOffDay();

    List<PlaceFileInfo> getPlaceFiles();
    List<PlaceMenu> getPlaceMenus();
    List<ReviewInfo> getReviews();

    interface PlaceFileInfo {
        String getUrl();
    }

    interface PlaceMenuInfo {
        String getMenu();
    }

    interface ReviewInfo {
        UserInfo getUser();
        String getContent();
    }

    interface UserInfo {
        String getName();
    }



}

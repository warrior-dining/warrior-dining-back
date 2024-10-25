package warriordiningback.api.admin.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface AdminPlaceService {

    Map<String, Object> placeList(String searchType, String searchKeyword, Pageable pageable);

    Map<String, Object> placeInfo(Long placeId);

    Map<String, Object> placeAdd(MultipartFile[] files, String menuItemJson, String placeInfoJason);

    Map<String, Object> placeEdit(Long placeId, MultipartFile[] files, String existingImagesJson, String menuItemJson, String placeInfoJason);
}

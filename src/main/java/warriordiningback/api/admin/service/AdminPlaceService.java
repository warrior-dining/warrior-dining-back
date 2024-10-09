package warriordiningback.api.admin.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface AdminPlaceService {

    public Map<String, Object> placeList();
    public Map<String, Object> placeInfo(Long placeId);
    public Map<String, Object> placeAdd(MultipartFile[] files, String menuItemJson, String placeInfoJason);
    public Map<String, Object> placeEdit(Long placeId,MultipartFile[] files, String existingImagesJson, String menuItemJson, String placeInfoJason);
}

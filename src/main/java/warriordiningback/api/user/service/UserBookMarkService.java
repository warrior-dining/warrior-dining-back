package warriordiningback.api.user.service;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface UserBookMarkService {

    public Map<String, Object> myBookMarkList(String email, Pageable pageable);
    public Map<String, Object> myBookMarkAdd(Map<String, Object> reqData);
    public Map<String, Object> myBookMarkDelete(String email, Long placeId);
}

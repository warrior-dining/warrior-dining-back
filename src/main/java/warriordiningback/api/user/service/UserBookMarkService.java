package warriordiningback.api.user.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface UserBookMarkService {

    public Map<String, Object> myBookMarkList(UserDetails userDetails, Pageable pageable);
    public Map<String, Object> myBookMarkAdd(Map<String, Object> reqData);
    public Map<String, Object> myBookMarkDelete(UserDetails userDetails, Long placeId);
}

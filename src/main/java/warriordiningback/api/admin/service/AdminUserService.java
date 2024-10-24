package warriordiningback.api.admin.service;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AdminUserService {

    public Map<String, Object> findByAll(String searchType, String searchKeyword, Pageable pageable);
    public Map<String, Object> findById(long userid);
    public Map<String, Object> handleRole(long userid,  Map<String, Object> role);
}

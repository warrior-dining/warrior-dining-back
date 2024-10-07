package warriordiningback.api.admin.service;

import java.util.Map;

public interface AdminUserService {

    public Map<String, Object> findByAll();
    public Map<String, Object> findById(long userid);
    public Map<String, Object> handleRole(long userid,  Map<String, Object> role);
}

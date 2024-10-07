package warriordiningback.api.admin.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warriordiningback.domain.user.Role;
import warriordiningback.domain.user.RoleRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class AdminUserServiceImp implements AdminUserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdminFileService adminFileService;

    @Override
    public Map<String, Object> findByAll() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", true);
        resultMap.put("results", userRepository.findAll());
        resultMap.put("message", "success");
        log.info("debug: {}",resultMap.toString());
        return resultMap;
    }

    @Override
    public Map<String, Object> findById(long userId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", true);
        resultMap.put("results", userRepository.findById(userId).orElse(null));
        resultMap.put("message", "success");
        return resultMap;
    }

    @Override
    public Map<String, Object> handleRole(long userId, Map<String, Object> role) {
        Map<String, Object> resultMap = new HashMap<>();
        String roleName = role.get("role").toString();
        if((boolean)role.get("type")) {
            User user = userRepository.findById(userId).orElse(null); // 권한을 부여할 사용자에 대한 정보를 객체에 담는다.
            Role grantRole = roleRepository.findByRole(roleName).orElse(null); // 부여할 롤에 대한 정보를 객체에 담는다.
            user.getRoles().add(grantRole);
            userRepository.save(user);
            resultMap.put("status", true);
            resultMap.put("message", "Success");
        } else if (!(boolean)role.get("type")) {
            User user = userRepository.findById(userId).orElse(null);
            Role revokeRole = roleRepository.findByRole(roleName).orElse(null);
            user.getRoles().remove(revokeRole);
            userRepository.save(user);
            resultMap.put("status", true);
            resultMap.put("message", "Success");
        } else {
            resultMap.put("status", false);
            resultMap.put("message", "Fail");
            log.info("잘못된 경로로 권한 설정을 시도중입니다.");
        }
        return resultMap;
    }
}

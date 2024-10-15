package warriordiningback.api.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = true)
public class UserBookMarkServiceImp implements UserBookMarkService {


    @Override
    public Map<String, Object> myBookMarkList() {
        return Map.of();
    }

    @Override
    public Map<String, Object> myBookMarkAdd() {
        return Map.of();
    }
}

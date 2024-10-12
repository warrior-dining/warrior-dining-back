package warriordiningback.api.admin.service;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AdminReviewService {

    public Map<String, Object> reviewList(String searchType, String searchKeyword, String sortType,Pageable pageable);
}

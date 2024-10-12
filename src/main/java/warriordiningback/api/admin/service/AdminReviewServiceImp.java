package warriordiningback.api.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import warriordiningback.domain.review.Review;
import warriordiningback.domain.review.ReviewRepository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AdminReviewServiceImp implements AdminReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Map<String, Object> reviewList (String searchType, String searchKeyword, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        Page<Review> searchReview;
        if(searchKeyword != null && searchKeyword != null){
            switch (searchType) {
                case "place":
                   searchReview = reviewRepository.findByPlaceName(searchKeyword, pageable);
                   break;
                case "user":
                    searchReview = reviewRepository.findByUserName(searchKeyword, pageable);
                    break;
                case "content":
                    searchReview = reviewRepository.findAllByContentContainingOrderByIdDesc(searchKeyword, pageable);
                    break;
                default:
                    searchReview = reviewRepository.findAll(pageable);
            }
        } else {
            searchReview = reviewRepository.findAll(pageable);
        }
        resultMap.put("status", true);
        resultMap.put("results", searchReview);
        return resultMap;
    }
}

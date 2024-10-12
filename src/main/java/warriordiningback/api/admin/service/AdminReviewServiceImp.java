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
    public Map<String, Object> reviewList (String searchType, String searchKeyword, String sortType, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        Page<Review> searchReview;
        if(searchKeyword != null && !searchKeyword.isEmpty()){
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
                    searchReview = reviewRepository.findAllByOrderByCreatedAtDesc(pageable);
                    break;
            }
        }  else if(sortType != null && !sortType.isEmpty()) {
            switch (sortType) {
                case "1":
                    searchReview = reviewRepository.findAllByRatingOrderByCreatedAtDesc(1, pageable);
                    break;
                case "2":
                    searchReview = reviewRepository.findAllByRatingOrderByCreatedAtDesc(2, pageable);
                    break;
                case "3":
                    searchReview = reviewRepository.findAllByRatingOrderByCreatedAtDesc(3, pageable);
                    break;
                case "4":
                    searchReview = reviewRepository.findAllByRatingOrderByCreatedAtDesc(4, pageable);
                    break;
                case "5":
                    searchReview = reviewRepository.findAllByRatingOrderByCreatedAtDesc(5, pageable);
                    break;
                case "true":
                    searchReview = reviewRepository.findAllByIsDeletedOrderByCreatedAtDesc(true, pageable);
                    break;
                case "false":
                    searchReview = reviewRepository.findAllByIsDeletedOrderByCreatedAtDesc(false, pageable);
                    break;
                case "desc":
                    searchReview = reviewRepository.findAllByOrderByCreatedAtDesc(pageable);
                    break;
                case "asc":
                    searchReview = reviewRepository.findAllByOrderByCreatedAtAsc(pageable);
                    break;
                default:
                    searchReview = reviewRepository.findAll(pageable);
                    break;
             }
        } else {
                searchReview = reviewRepository.findAll(pageable);
        }

        resultMap.put("status", true);
        resultMap.put("results", searchReview);
        return resultMap;
    }
}

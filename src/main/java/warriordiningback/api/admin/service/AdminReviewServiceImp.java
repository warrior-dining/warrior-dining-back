package warriordiningback.api.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warriordiningback.domain.review.Review;
import warriordiningback.domain.review.ReviewRepository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AdminReviewServiceImp implements AdminReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    @Transactional
    public Map<String, Object> reviewList (String searchType, String searchKeyword, String sortType, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        Page<Review> searchReviews;
        if(searchKeyword != null && !searchKeyword.isEmpty()){
            switch (searchType) {
                case "place":
                   searchReviews = reviewRepository.findAllByPlaceName(searchKeyword, pageable);
                   break;
                case "user":
                    searchReviews = reviewRepository.findAllByUserName(searchKeyword, pageable);
                    break;
                case "content":
                    searchReviews = reviewRepository.findAllByContentContainingOrderByIdDesc(searchKeyword, pageable);
                    break;
                default:
                    searchReviews = reviewRepository.findAllByOrderByCreatedAtDesc(pageable);
                    break;
            }
        }  else if(sortType != null && !sortType.isEmpty()) {
            switch (sortType) {
                case "1":
                    searchReviews = reviewRepository.findAllByRatingOrderByCreatedAtDesc(1, pageable);
                    break;
                case "2":
                    searchReviews = reviewRepository.findAllByRatingOrderByCreatedAtDesc(2, pageable);
                    break;
                case "3":
                    searchReviews = reviewRepository.findAllByRatingOrderByCreatedAtDesc(3, pageable);
                    break;
                case "4":
                    searchReviews = reviewRepository.findAllByRatingOrderByCreatedAtDesc(4, pageable);
                    break;
                case "5":
                    searchReviews = reviewRepository.findAllByRatingOrderByCreatedAtDesc(5, pageable);
                    break;
                case "true":
                    searchReviews = reviewRepository.findAllByIsDeletedOrderByCreatedAtDesc(true, pageable);
                    break;
                case "false":
                    searchReviews = reviewRepository.findAllByIsDeletedOrderByCreatedAtDesc(false, pageable);
                    break;
                case "desc":
                    searchReviews = reviewRepository.findAllByOrderByCreatedAtDesc(pageable);
                    break;
                case "asc":
                    searchReviews = reviewRepository.findAllByOrderByCreatedAtAsc(pageable);
                    break;
                default:
                    searchReviews = reviewRepository.findAll(pageable);
                    break;
             }
        } else {
                searchReviews = reviewRepository.findAll(pageable);
        }
        resultMap.put("status", true);
        resultMap.put("results", searchReviews);
        return resultMap;
    }

    @Transactional
    public Map<String, Object> updateReviewStatus(Long reviewId){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", false);
        Review updateReview =  reviewRepository.findById(reviewId).orElseThrow(()-> new RuntimeException("Review Not Found"));
        if(updateReview != null && !updateReview.isDeleted()){
            resultMap.put("status", true);
            updateReview.updateIsdelete(true);
            resultMap.put("results", updateReview);
        } else {
            resultMap.put("error", "리뷰를 찾을 수 없습니다.");
        }
        return resultMap;
    }
}

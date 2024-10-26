package warriordiningback.api.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warriordiningback.domain.review.Review;
import warriordiningback.domain.review.ReviewQueryRepository;
import warriordiningback.domain.review.ReviewRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminReviewServiceImp implements AdminReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;

    @Override
    public Map<String, Object> reviewList(String searchType, String searchKeyword, String sortType, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        Page<Review> searchReviews;
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            searchReviews = switch (searchType) {
                case "place" -> reviewQueryRepository.findAllByPlaceName(searchKeyword, pageable);
                case "user" -> reviewQueryRepository.findAllByUserName(searchKeyword, pageable);
                case "content" -> reviewRepository.findAllByContentContainingOrderByIdDesc(searchKeyword, pageable);
                default -> findAllByOrderByCreatedAtDesc(pageable);
            };
        } else if (sortType != null && !sortType.isEmpty()) {
            searchReviews = switch (sortType) {
                case "1" -> findAllByRatingOrderByCreatedAtDesc(1, pageable);
                case "2" -> findAllByRatingOrderByCreatedAtDesc(2, pageable);
                case "3" -> findAllByRatingOrderByCreatedAtDesc(3, pageable);
                case "4" -> findAllByRatingOrderByCreatedAtDesc(4, pageable);
                case "5" -> findAllByRatingOrderByCreatedAtDesc(5, pageable);
                case "true" -> findAllByIsDeletedOrderByCreatedAtDesc(true, pageable);
                case "false" -> findAllByIsDeletedOrderByCreatedAtDesc(false, pageable);
                case "desc" -> findAllByOrderByCreatedAtDesc(pageable);
                case "asc" -> reviewRepository.findAllByOrderByCreatedAtAsc(pageable);
                default -> findAllByReview(pageable);
            };
        } else {
            searchReviews = findAllByReview(pageable);
        }
        resultMap.put("status", true);
        resultMap.put("results", searchReviews);
        return resultMap;
    }

    @Transactional
    public Map<String, Object> updateReviewStatus(Long reviewId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", false);
        Review updateReview = reviewRepository.findById(reviewId).orElseThrow(()
                -> new DiningApplicationException(ErrorCode.REVIEW_NOT_FOUND));
        if (updateReview != null && !updateReview.isDeleted()) {
            resultMap.put("status", true);
            updateReview.updateIsdelete(true);
            resultMap.put("results", updateReview);
        } else {
            resultMap.put("error", "리뷰를 찾을 수 없습니다.");
        }
        return resultMap;
    }

    private Page<Review> findAllByReview(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    private Page<Review> findAllByOrderByCreatedAtDesc(Pageable pageable) {
        return reviewRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    private Page<Review> findAllByRatingOrderByCreatedAtDesc(int rating, Pageable pageable) {
        return reviewRepository.findAllByRatingOrderByCreatedAtDesc(rating, pageable);
    }

    private Page<Review> findAllByIsDeletedOrderByCreatedAtDesc(boolean isDeleted, Pageable pageable) {
        return reviewRepository.findAllByIsDeletedOrderByCreatedAtDesc(isDeleted, pageable);
    }
}

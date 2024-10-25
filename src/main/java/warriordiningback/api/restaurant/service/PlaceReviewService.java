package warriordiningback.api.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import warriordiningback.api.restaurant.dto.ReviewData;
import warriordiningback.domain.review.ReviewQueryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceReviewService {

    @Autowired
    private ReviewQueryRepository reviewQueryRepository;

    public List<ReviewData> findByReview() {
        List<ReviewData> top10ReviewData = reviewQueryRepository.findTop10ReviewData();
        return top10ReviewData.stream().limit(12).toList();
    }
}

package warriordiningback.api.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warriordiningback.api.admin.dto.InquiryCountDto;
import warriordiningback.domain.inquiry.InquiryRepository;
import warriordiningback.domain.place.Place;
import warriordiningback.domain.place.PlaceQueryRepository;
import warriordiningback.domain.reservation.ReservationQueryRepository;
import warriordiningback.domain.reservation.ReservationRepository;
import warriordiningback.domain.review.ReviewRepository;
import warriordiningback.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AdminMainServiceImp implements AdminMainService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationQueryRepository reservationQueryRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private PlaceQueryRepository placeQueryRepository;

    @Override
    public Map<String, Object> mainPage() {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> results = new HashMap<>();
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);

        // 최근 30일 이내 생성된 예약 건수
        int countRecentReservations = reservationQueryRepository.countRecentReservations(thirtyDaysAgo);
        results.put("countRecentReservations", countRecentReservations);

        // 최근 30일 이내 생성된 리뷰의 별점 평균
        double avgRecentRating = reviewRepository.avgRecentRating(thirtyDaysAgo);
        results.put("avgRecentRating", avgRecentRating);

        // 최근 30일 이내 가입한 사용자 카운트
        int countRecentJoinUser = userRepository.countRecentJoinUser(thirtyDaysAgo);
        results.put("countRecentJoinUser", countRecentJoinUser);

        // 문의사항 답변처리 상태별 카운트
        List<InquiryCountDto> countInquiriesByCode = inquiryRepository.countInquiriesByCode();
        results.put("countInquiriesByCode", countInquiriesByCode);

        // 총 예약 건수
        long reservationTotalCount = reservationRepository.count();
        results.put("reservationTotalCount", reservationTotalCount);

        // 총 리뷰 건수
        long reviewTotalCount = reviewRepository.count();
        results.put("reviewTotalCount", reviewTotalCount);

        // 총 회원 수
        long userTotalCount = userRepository.count();
        results.put("userTotalCount", userTotalCount);

        // 최근 방문자 수

        // 최근 등록된 음식점
        List<Place> placeRecent = placeQueryRepository.findTop5ByOrderByCreatedAtDESC();
        results.put("placeRecent", placeRecent);

        // resultMap에 조회된 결과 담기
        resultMap.put("status", true);
        resultMap.put("results", results);
        return resultMap;
    }
}

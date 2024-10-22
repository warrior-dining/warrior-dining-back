package warriordiningback.api.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warriordiningback.api.user.dto.UserReservationAddRequest;
import warriordiningback.api.user.dto.UserReservationEditRequest;
import warriordiningback.api.user.dto.UserReservationInfoResponse;
import warriordiningback.api.user.dto.UserReservationListResponse;
import warriordiningback.domain.Code;
import warriordiningback.domain.CodeRepository;
import warriordiningback.domain.place.Place;
import warriordiningback.domain.place.PlaceRepository;
import warriordiningback.domain.reservation.Reservation;
import warriordiningback.domain.reservation.ReservationRepository;
import warriordiningback.domain.review.ReviewRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserReservationServiceImp implements UserReservationService{

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private CodeRepository codeRepository;

    @Override
    public Map<String, Object> myReservationList(String email, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", false);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));
        Page<Reservation> myReservations = reservationRepository.findAllByUserOrderByReservationDateDesc(user, pageable);
        List<UserReservationListResponse> userReservationListResponse = new ArrayList<>();

        for (Reservation row : myReservations.getContent()) {
        	boolean reviewExists = reviewRepository.existsByReservationId(row.getId()); // 리뷰 존재 여부 확인
            UserReservationListResponse myReservation = new UserReservationListResponse(row);
            myReservation.setReviewExists(reviewExists); // 리뷰 존재 여부 설정
            userReservationListResponse.add(myReservation);
        }
        Page<UserReservationListResponse> results = new PageImpl<>(userReservationListResponse, myReservations.getPageable(), myReservations.getTotalElements());

        resultMap.put("status", true);
        resultMap.put("results", results);
        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> reservationAdd(UserReservationAddRequest reqData) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", false);
        User userInfo;
        Place placeInfo;
        Code code;
        Reservation reservationInfo = new Reservation();
        if(reqData != null){
            userInfo = userRepository.findByEmail(reqData.getUserEmail()).orElseThrow(() -> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));
            placeInfo = placeRepository.findById(reqData.getPlaceId()).orElseThrow(() -> new DiningApplicationException(ErrorCode.PLACE_NOT_FOUND));
            code = codeRepository.findById(14L).orElseThrow(() -> new DiningApplicationException(ErrorCode.CODE_NOT_FOUND));

            /* Reservation Entity에 맞춰 날짜, 시간 타입 포맷 변경. 추후에 엔티티 수정시 같이 수정할 것 */
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date reservationDate = dateFormat.parse(reqData.getReservationDate());
                Date reservationTime = dateTimeFormat.parse(reqData.getReservationTime());

                reservationInfo.create(reservationDate, reservationTime, reqData.getCount(), reqData.getOrderNote(), userInfo, placeInfo, code);
                reservationRepository.save(reservationInfo);

                resultMap.put("status", true);
                resultMap.put("results", reservationInfo);
            } catch (ParseException e) {
                resultMap.put("comment", "날짜 또는 시간 형식이 올바르지 않습니다.");
            };
        } else {
            resultMap.put("comment", "실패다.");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> myReservationInfo(Long reservationId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", false);

        if(reservationId != null){
            Reservation reservationInfo = reservationRepository.findById(reservationId).orElseThrow(()-> new DiningApplicationException(ErrorCode.RESERVATION_NOT_FOUND));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String formattedDate = dateFormat.format(reservationInfo.getReservationDate());
            String formattedTime = timeFormat.format(reservationInfo.getReservationTime());

            UserReservationInfoResponse userReservationInfo =
                    new UserReservationInfoResponse(reservationInfo.getId(),
                                                    reservationInfo.getPlace().getName(),
                                                    reservationInfo.getPlace().getStartTime(),
                                                    reservationInfo.getPlace().getEndTime(),
                                                    formattedDate,
                                                    formattedTime,
                                                    reservationInfo.getCount(),
                                                    reservationInfo.getOrderNote());
            resultMap.put("status", true);
            resultMap.put("results", userReservationInfo);
        } else {
            resultMap.put("comment", "예약 번호가 없습니다.");
        }
        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> myreservationEdit(Long reservationId, UserReservationEditRequest reqData) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", false);

        if(reservationId != null){
            Reservation reservationInfo = reservationRepository.findById(reservationId).orElseThrow(()-> new DiningApplicationException(ErrorCode.RESERVATION_NOT_FOUND));

            /* Reservation Entity에 맞춰 날짜, 시간 타입 포맷 변경. 추후에 엔티티 수정시 같이 수정할 것 */
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date reservationDate = dateFormat.parse(reqData.getReservationDate());
                Date reservationTime = dateTimeFormat.parse(reqData.getReservationTime());

                reservationInfo.edit(reservationDate, reservationTime, reqData.getCount(), reqData.getOrderNote());
                resultMap.put("status", true);
                resultMap.put("results", reservationInfo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            resultMap.put("comment", "예약 번호가 없습니다.");
        }
        return resultMap;
    }

}

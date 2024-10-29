package warriordiningback.api.owner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warriordiningback.api.code.CodeService;
import warriordiningback.api.owner.dto.OwnerReservationListResponse;
import warriordiningback.api.user.dto.UserReservationListResponse;
import warriordiningback.domain.Code;
import warriordiningback.domain.CodeRepository;
import warriordiningback.domain.reservation.Reservation;
import warriordiningback.domain.reservation.ReservationQueryRepository;
import warriordiningback.domain.reservation.ReservationRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OwnerReservationServiceImp implements OwnerReservationService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationQueryRepository reservationQueryRepository;
    private final CodeService codeService;

    @Override
    public Map<String, Object> ownerMain(String status, UserDetails userDetails, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        Page<Reservation> ownerReservations;
        List<OwnerReservationListResponse> responseList = new ArrayList<>();
        resultMap.put("status", false);

        if (status != null && !status.isEmpty()) {
            Long statusCode = Long.parseLong(status);
            User owner = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));
            ownerReservations = reservationQueryRepository.findAllByOwner(owner.getId(), statusCode, pageable);
        } else {
            User owner = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));
            ownerReservations = reservationQueryRepository.findAllByOwnerNotStatus(owner.getId(), pageable);
        }
        for (Reservation row : ownerReservations.getContent()) {
            OwnerReservationListResponse response = new OwnerReservationListResponse(row);
            responseList.add(response);
        }
        Page<OwnerReservationListResponse> results = new PageImpl<>(responseList, ownerReservations.getPageable(), ownerReservations.getTotalElements());
        resultMap.put("status", true);
        resultMap.put("results", results);

        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> updateReservation(Long id, Map<String, Long> statusMap) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", false);

        Code code;
        if (statusMap.get("status") != null && statusMap.get("status") == 14) {
            code = codeService.findCodeById(13L);
        } else {
            resultMap.put("message", "잘못 요청한듯");
            return resultMap;
        }

        if (reservationRepository.existsById(id)) {
            Reservation reservations = reservationRepository.findById(id).orElseThrow(() -> new DiningApplicationException(ErrorCode.RESERVATION_NOT_FOUND));
            reservations.updateStatus(code);
            resultMap.put("status", true);
            resultMap.put("results", reservations);
        }
        return resultMap;
    }
}
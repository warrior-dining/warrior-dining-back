package warriordiningback.api.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warriordiningback.api.admin.dto.ReservationResponse;
import warriordiningback.api.code.CodeService;
import warriordiningback.domain.Code;
import warriordiningback.domain.reservation.Reservation;
import warriordiningback.domain.reservation.ReservationQueryRepository;
import warriordiningback.domain.reservation.ReservationRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminReservationServiceImp implements AdminReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationQueryRepository reservationQueryRepository;

    private final CodeService codeService;

    @Override
    public Map<String, Object> reservationList(String searchType, String searchKeyword, String status, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", false);
        Page<Reservation> searchReservation;

        if (status != null && !status.isEmpty()) {
            Code code = codeService.findCodeById(Long.parseLong(status));
            searchReservation = reservationRepository.findAllByCodeOrderByCreatedAtDesc(pageable, code);
            resultMap.put("status", true);
        } else if (searchKeyword != null && !searchKeyword.isEmpty()) {
            searchReservation = switch (searchType) {
                case "id" -> reservationRepository.findById(Long.parseLong(searchKeyword), pageable);
                case "name" -> reservationQueryRepository.findAllByUserName(searchKeyword, pageable);
                case "place" -> reservationQueryRepository.findAllByPlaceName(searchKeyword, pageable);
                default -> reservationRepository.findAllByOrderByCreatedAtDesc(pageable);
            };
            resultMap.put("status", true);
        } else {
            searchReservation = reservationRepository.findAllByOrderByCreatedAtDesc(pageable);
            resultMap.put("status", true);
        }

        List<ReservationResponse> reservationResponses = new ArrayList<>();
        for (Reservation row : searchReservation.getContent()) {
            ReservationResponse adminReservationResponse = new ReservationResponse(row);
            reservationResponses.add(adminReservationResponse);
        }

        Page<ReservationResponse> results = new PageImpl<>(reservationResponses, searchReservation.getPageable(), searchReservation.getTotalElements());

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

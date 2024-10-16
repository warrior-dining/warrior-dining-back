package warriordiningback.api.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warriordiningback.api.user.dto.BookMarkResponse;
import warriordiningback.domain.place.Place;
import warriordiningback.domain.place.PlaceRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class UserBookMarkServiceImp implements UserBookMarkService {

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public Map<String, Object> myBookMarkList(String email, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", false);
        if (UserRepository.existsByEmail(email)) {
            Page<Object[]> res = userRepository.findUserBookmarksWithAvgRating(email, pageable);
            List<BookMarkResponse> bookMarkResponses = new ArrayList<>();
            for (Object[] row : res) {
                BookMarkResponse bookMark = new BookMarkResponse();
                bookMark.setPlaceId(Long.parseLong(row[0].toString()));
                bookMark.setPlaceName(row[1].toString());
                bookMark.setAddressNew(row[2].toString());
                bookMark.setPhone(row[3].toString());
                bookMark.setAvgRating(Double.parseDouble(row[4].toString()));
                bookMarkResponses.add(bookMark);
            }
            // PageImpl을 사용하여 페이징 정보를 포함한 결과를 생성
            Page<BookMarkResponse> pageResult = new PageImpl<>(bookMarkResponses, res.getPageable(), res.getTotalElements());
            resultMap.put("status", true);
            resultMap.put("results", pageResult);
        } else {
            throw new DiningApplicationException(ErrorCode.USER_NOT_FOUND);
        }
        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> myBookMarkAdd(Map<String, Object> reqData) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        User user = userRepository.findByEmail(reqData.get("email").toString()).orElseThrow(() -> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));
        Place place = placeRepository.findById(Long.parseLong(reqData.get("placeId").toString())).orElseThrow(() -> new DiningApplicationException(ErrorCode.PLACE_NOT_FOUND));
        user.getBookmarks().add(place);
        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> myBookMarkDelete(String email, Long placeId) {
        Map<String, Object> resultMap = new HashMap<>();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new DiningApplicationException(ErrorCode.PLACE_NOT_FOUND));
        user.getBookmarks().remove(place);

        resultMap.put("status", true);
        resultMap.put("message", "Bookmark deleted successfully");
        return resultMap;
    }
}

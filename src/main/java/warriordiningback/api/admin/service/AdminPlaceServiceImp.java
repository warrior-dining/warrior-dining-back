package warriordiningback.api.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import warriordiningback.domain.Code;
import warriordiningback.domain.CodeRepository;
import warriordiningback.domain.place.Place;
import warriordiningback.domain.place.PlaceMenu;
import warriordiningback.domain.place.PlaceMenuRepository;
import warriordiningback.domain.place.PlaceRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AdminPlaceServiceImp implements AdminPlaceService {



    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceMenuRepository placeMenuRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private AdminFileService adminFileService;

    @Override
    public Map<String, Object> placeList() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", true);
        resultMap.put("results", placeRepository.findAll());
        resultMap.put("message", "success");
        return resultMap;
    }

    @Override
    public Map<String, Object> placeInfo(Long placeId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", true);
        resultMap.put("results", placeRepository.findById(placeId).orElseThrow(()->new RuntimeException("그런 아이디 없다.")));
        resultMap.put("message", "success");
        return resultMap;
    }

    @Override
    public Map<String, Object> placeAdd(MultipartFile[] files, String menuItemsJson, String placeInfoJson) {

        // JSON 문자열을 List<Map>으로 변환
        // 프론트엔드에서 FormData를 사용하여 여러가지의 데이터를 한번에 벡엔트로 Request할 경우
        // 파라미터를 string 으로 받고 이후 데이터 타입에 맞는 후처리를 해줘야한다.
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> menuItems;
        Map<String, Object> placeInfo;
        try {
            menuItems = objectMapper.readValue(menuItemsJson, new TypeReference<List<Map<String, Object>>>() {});
            placeInfo = objectMapper.readValue(placeInfoJson, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            log.error("JSON 변환 실패", e);
            return Collections.singletonMap("status", false);
        }
        /* Place 테이블에 인서트 하는 로직 */
        log.info("========== 음식점 정보 저장 로직 시작 ============");
        User userInfo = userRepository.findByEmail(placeInfo.get("email").toString()).orElseThrow(()-> new RuntimeException("그런 사용자 없음."));
        Code category = codeRepository.findById(Long.valueOf(placeInfo.get("category").toString())).orElseThrow(()-> new RuntimeException("그런 코드 없음"));
        Place place = Place.create(placeInfo.get("name").toString(),
                userInfo,
                category,
                placeInfo.get("location").toString(),
                placeInfo.get("phone").toString(),
                placeInfo.get("starttime").toString(),
                placeInfo.get("endtime").toString(),
                placeInfo.get("offday").toString(),
                placeInfo.get("description").toString());
        place = placeRepository.save(place);
        log.info("========== 음식점 정보 저장 로직 끝 ============");

        /* Menu 테이블에 인서트 하는 로직 */
        log.info("========== 음식점 메뉴 정보 저장 로직 시작 ============");
        Long insertPlaceId = place.getId();
        for(int i = 0; i < menuItems.size(); i++){
            Map<String, Object> menuItem = menuItems.get(i);
            PlaceMenu menu = PlaceMenu.create(place, menuItem.get("name").toString(), Integer.parseInt(menuItem.get("price").toString()), Integer.parseInt(menuItem.get("id").toString()));
            placeMenuRepository.save(menu);
        }
        log.info("========== 음식점 메뉴 정보 저장 로직 끝 =============");

        /* 이미지 파일 테이블에 인서트 하는 로직 */
        log.info("========== 음식점 사진 정보 저장 로직 시작 ============");
        adminFileService.fileUpload(files, place);
        log.info("========== 음식점 사진 정보 저장 로직 끝 =============");

        Place resPlace = placeRepository.findById(place.getId()).orElseThrow(()-> new RuntimeException("그런 아이디 없다."));
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", true);
        resultMap.put("results", resPlace);
        resultMap.put("message", "success");
        return resultMap;
    }
}

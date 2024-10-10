package warriordiningback.api.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import warriordiningback.domain.Code;
import warriordiningback.domain.CodeRepository;
import warriordiningback.domain.place.*;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private PlaceFileRepository placeFileRepository;

    @Override
    public Map<String, Object> placeList(Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        Page<Place> resPlaces = placeRepository.findAllByOrderByNameAsc(pageable);

        resultMap.put("status", true);
        resultMap.put("results", resPlaces);
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
        placeRepository.save(place);
        /* Menu 테이블에 인서트 하는 로직 */
        for(int i = 0; i < menuItems.size(); i++){
            Map<String, Object> menuItem = menuItems.get(i);
            PlaceMenu menu = PlaceMenu.create(place, menuItem.get("name").toString(), Integer.parseInt(menuItem.get("price").toString()));
            placeMenuRepository.save(menu);
        }

        /* 이미지 파일 테이블에 인서트 하는 로직 */
        adminFileService.fileUpload(files, place);

        Place resPlace = placeRepository.findById(place.getId()).orElseThrow(()-> new RuntimeException("그런 아이디 없다."));
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", true);
        resultMap.put("results", resPlace);
        resultMap.put("message", "success");
        return resultMap;
    }

    @Override
    public Map<String, Object> placeEdit(Long placeId, MultipartFile[] files, String existingImagesJson, String menuItemsJson, String placeInfoJson) {

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> existingImages;
        List<Map<String, Object>> menuItems;
        Map<String, Object> placeInfo;
        try {
            existingImages = objectMapper.readValue(existingImagesJson, new TypeReference<List<Map<String, Object>>>() {});
            menuItems = objectMapper.readValue(menuItemsJson, new TypeReference<List<Map<String, Object>>>() {});
            placeInfo = objectMapper.readValue(placeInfoJson, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            log.error("JSON 변환 실패", e);
            return Collections.singletonMap("status", false);
        }

        /* 음식점 테이블 데이터 수정 작업 */
        Place editPlace = placeRepository.findById(placeId).orElseThrow(()-> new RuntimeException("해당 아이디 음식점 없음"));
        User userInfo = userRepository.findByEmail(placeInfo.get("email").toString()).orElseThrow(()-> new RuntimeException("그런 사용자 없음."));
        Code category = codeRepository.findById(Long.valueOf(placeInfo.get("category").toString())).orElseThrow(()-> new RuntimeException("그런 코드 없음"));
        editPlace.update(placeInfo.get("name").toString(), userInfo, category,
                placeInfo.get("location").toString(),
                placeInfo.get("phone").toString(),
                placeInfo.get("starttime").toString(),
                placeInfo.get("endtime").toString(),
                placeInfo.get("offday").toString(),
                placeInfo.get("description").toString());

        /* 음식점 메뉴 테이블 데이터 수정 */
        List<PlaceMenu> asIsMenu = placeMenuRepository.findByPlaceId(placeId);
        if(menuItems.size() > asIsMenu.size() ){
            for(int i = 0; i < menuItems.size(); i++){
                if(i < asIsMenu.size()) {
                    Map<String, Object> menuItem = menuItems.get(i);
                    PlaceMenu toBeMenu = asIsMenu.get(i);
                    toBeMenu.update(menuItem.get("menu").toString(), Integer.parseInt(menuItem.get("price").toString()));
                } else {
                    Map<String, Object> menuItem = menuItems.get(i);
                    PlaceMenu toBeMenu = PlaceMenu.create(editPlace, menuItem.get("menu").toString(), Integer.parseInt(menuItem.get("price").toString()));
                    placeMenuRepository.save(toBeMenu);
                }
            }
        } else if( menuItems.size() == asIsMenu.size() ) {
            for(int i = 0; i < menuItems.size(); i++) {
                Map<String, Object> menuItem = menuItems.get(i);
                PlaceMenu toBeMenu = asIsMenu.get(i);
                toBeMenu.update(menuItem.get("menu").toString(), Integer.parseInt(menuItem.get("price").toString()));
            }
        } else {
            for(int i = 0; i < asIsMenu.size(); i++) {
                if(i < menuItems.size()){
                    Map<String, Object> menuItem = menuItems.get(i);
                    PlaceMenu toBeMenu = asIsMenu.get(i);
                    toBeMenu.update(menuItem.get("menu").toString(), Integer.parseInt(menuItem.get("price").toString()));
                } else {
                    placeMenuRepository.deleteById(asIsMenu.get(i).getId());
                }
            }
        }

// 기존 이미지 처리
        List<PlaceFile> asIsFile = placeFileRepository.findByPlaceId(placeId);

        // 1. existingImages 리스트에 없는 파일 삭제
        Set<String> existingImageIds = existingImages.stream()
                .map(image -> image.get("id").toString()) // 각 이미지에 ID가 있다고 가정
                .collect(Collectors.toSet());

        for (PlaceFile placeFile : asIsFile) {
            if (!existingImageIds.contains(placeFile.getId().toString())) {
                placeFileRepository.deleteById(placeFile.getId());
            }
        }

        if (files != null && files.length > 0) {
            adminFileService.fileUpload(files, editPlace);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", true);
        resultMap.put("results", editPlace);
        resultMap.put("message", "success");
        return resultMap;
    }


}

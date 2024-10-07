package warriordiningback.api.admin.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import warriordiningback.api.admin.dto.AdminFileData;
import warriordiningback.components.FileComponent;
import warriordiningback.domain.place.Place;
import warriordiningback.domain.place.PlaceFile;
import warriordiningback.domain.place.PlaceFileRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminFileService {

    @Autowired
    private FileComponent fileComponent;

    @Autowired
    private PlaceFileRepository placeFileRepository;

    public Map<String, Object> fileUpload(MultipartFile[] files, Place placeInfo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        AdminFileData dto = new AdminFileData();
        resultMap.put("state", false);

        List<String> list = new ArrayList<>();
        for(int i = 0; i < files.length; i++) {
            dto = findByNo(save(files[i],fileComponent.upload2(files[i]), placeInfo, i));
            list.add(dto.getUrl());
        }
        if(list.size() > 0) {
            resultMap.put("state", true);
            resultMap.put("list", list);
            resultMap.put("comment", "저장 성공");
        }
        return resultMap;
    }

    private long save(MultipartFile file, String url, Place placeInfo, int index){
//        log.info("File Service 왔다.");
        String viewUrl = "http://localhost:8080/api/admin/places/view?url=";
        Map<String, Object> data = new HashMap<>();
        data = fileComponent.setFile(file);
        PlaceFile placeFile= PlaceFile.create( placeInfo, data.get("Name").toString(),
                        data.get("Extension").toString(), viewUrl + url,
                        data.get("NewName").toString(), url,
                        "image/png", index);
        placeFile = placeFileRepository.save(placeFile);
        return placeFile.getId();
    }

    public AdminFileData findByNo(long id){
        PlaceFile placeFile = placeFileRepository.findById(id).orElseThrow(()-> new RuntimeException("그런 ID 업다."));
        AdminFileData adminFileData = AdminFileData.builder().id(placeFile.getId())
                .name(placeFile.getName())
                .extension(placeFile.getExtension())
                .url(placeFile.getUrl())
                .saveName(placeFile.getSaveName())
                .savePath(placeFile.getSavePath())
                .mediaType(placeFile.getMediaType())
                .viewOrder(placeFile.getViewOrder())
                .build();
        return adminFileData;
    }

    public Map<String, Object> findAll(){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("state", false);

        List<PlaceFile> list = new ArrayList<>();
        list = placeFileRepository.findAll();
        if(list.size() > 0) {
            resultMap.put("state", true);
            resultMap.put("list", list);
        }
        return resultMap;
    }
}

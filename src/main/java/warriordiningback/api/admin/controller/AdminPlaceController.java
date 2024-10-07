package warriordiningback.api.admin.controller;

import warriordiningback.api.admin.service.AdminPlaceService;
import warriordiningback.components.FileComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin/places")
public class AdminPlaceController {


    @Autowired
    private AdminPlaceService adminPlaceService;

    @Autowired
    private FileComponent fileComponent;

    @GetMapping("/")
    public Map<String, Object> placeList(){
        return adminPlaceService.placeList();
    }

    @GetMapping("/info/{placeId:[0-9]+}")
    public Map<String, Object> placeInfo(@PathVariable("placeId") Long placeId){
        return adminPlaceService.placeInfo(placeId);
    }

    @PostMapping("/")
    public Map<String, Object> placeAdd(@RequestParam("file") MultipartFile[] files,
                                      @RequestParam("menu") String menuItemJson,
                                      @RequestParam("place") String placeInfoJson){

        return adminPlaceService.placeAdd(files, menuItemJson, placeInfoJson);
    }
//
//    @PostMapping("/filesUpload")
//    public Map<String, Object> filesUpload(@RequestParam("file") MultipartFile[] files) {
//        return fileService.fileUpload(files);
//    }
//
//    @GetMapping("/filesRead")
//    public Map<String, Object> filesRead() {
//        return fileService.findAll();
//    }
////
    @GetMapping("/view")
    public ResponseEntity<?> view(@RequestParam("url") String url) {
        return fileComponent.getFile(url);
    }
}

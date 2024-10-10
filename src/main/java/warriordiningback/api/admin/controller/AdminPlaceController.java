package warriordiningback.api.admin.controller;

import org.springframework.data.domain.Pageable;
import warriordiningback.api.admin.service.AdminPlaceService;
import warriordiningback.components.FileComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/admin/places")
public class AdminPlaceController {


    @Autowired
    private AdminPlaceService adminPlaceService;

    @Autowired
    private FileComponent fileComponent;

    @GetMapping("/")
    public Map<String, Object> placeList(@RequestParam(name = "type", required = false) String searchType,
                                        @RequestParam(name = "keyword", required = false) String searchKeyword,
                                         Pageable pageable){
        return adminPlaceService.placeList(searchType, searchKeyword, pageable);
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

    @GetMapping("/view")
    public ResponseEntity<?> view(@RequestParam("url") String url) {
        return fileComponent.getFile(url);
    }

    @PutMapping("/{placeId:[0-9]+}")
    public Map<String, Object> placeEdit(@PathVariable("placeId") Long placeId,
                                            @RequestParam("file") MultipartFile[] files,
                                          @RequestParam("existingImages") String existingImagesJson,
                                          @RequestParam("menu") String menuItemJson,
                                          @RequestParam("place") String placeInfoJson){

        return adminPlaceService.placeEdit(placeId, files, existingImagesJson, menuItemJson, placeInfoJson);
    }
}

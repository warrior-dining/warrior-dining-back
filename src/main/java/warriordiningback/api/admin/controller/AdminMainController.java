package warriordiningback.api.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warriordiningback.api.admin.docs.AdminMainControllerDocs;
import warriordiningback.api.admin.service.AdminMainService;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminMainController implements AdminMainControllerDocs {

    private final AdminMainService adminMainService;

    @GetMapping
    public Map<String, Object> mainPage() {
        return adminMainService.mainPage();
    }
}

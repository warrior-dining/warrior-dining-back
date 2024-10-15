package warriordiningback.api.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warriordiningback.api.admin.service.AdminMainService;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminMainController {

    @Autowired
    private AdminMainService adminMainService;

    @GetMapping("/")
    public Map<String, Object> mainPage() {
        return adminMainService.mainPage();
    }


}

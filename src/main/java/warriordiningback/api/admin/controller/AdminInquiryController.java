package warriordiningback.api.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import warriordiningback.api.admin.service.AdminInquiryService;

import java.util.Map;


@RequestMapping("/api/admin/inquiries")
@RestController
@RequiredArgsConstructor
public class AdminInquiryController {

    private final AdminInquiryService adminInquiryService;

    @GetMapping
    public Map<String, Object> inquiryList(@RequestParam(name = "type", required = false) String searchType,
                                           @RequestParam(name = "keyword", required = false) String searchKeyword, Pageable pageable) {
        return adminInquiryService.inquiryList(searchType, searchKeyword, pageable);
    }

    @GetMapping("/{id:[0-9]+}")
    public Map<String, Object> inquiryInfo(@PathVariable(name = "id") Long id) {
        return adminInquiryService.inquiryInfo(id);
    }

    @PostMapping("/{id:[0-9]+}")
    public Map<String, Object> inquiryAnswerSave(@PathVariable("id") Long id, @RequestBody Map<String, Object> content) {
        return adminInquiryService.inquiryAnswerSave(id, content);
    }
}

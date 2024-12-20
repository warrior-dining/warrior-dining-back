package warriordiningback.api.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import warriordiningback.api.user.service.UserInquiryService;

import java.util.Map;

@RestController
@RequestMapping("/api/user/inquiries")
@RequiredArgsConstructor
public class UserInquiryController {

    private final UserInquiryService inquiryService;

    @GetMapping
    public Map<String, Object> inquiryList(@AuthenticationPrincipal UserDetails userDetails, Pageable pageable) {
        return inquiryService.inquiryList(userDetails, pageable);
    }

    @GetMapping("/{id:[0-9]+}")
    public Map<String, Object> inquiryInfo(@PathVariable("id") Long id) {
        return inquiryService.inquiryInfo(id);
    }

    @PutMapping("/{id:[0-9]+}")
    public Map<String, Object> inquiryInfoEdit(@PathVariable("id") Long id,
                                               @RequestBody Map<String, Object> inquiry) {
        return inquiryService.inquiryInfoEdit(id, inquiry);
    }

    @PostMapping
    public Map<String, Object> newInquiry(@RequestBody Map<String, Object> newInquiries) {
        return inquiryService.newInquiry(newInquiries);
    }

}

package warriordiningback.api.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import warriordiningback.api.user.service.UserInquiryService;

@RestController
@RequestMapping("/api/member/inquiries")
public class UserInquiryController {
	
	@Autowired
	private UserInquiryService inquiryService;
	
	@GetMapping("/")
	public Map<String, Object> inquiryList(@RequestParam("email") String email, Pageable pageable){
		return inquiryService.inquiryList(email, pageable);
	}

	@GetMapping("/{id:[0-9]+}")
	public Map<String, Object> inquiryInfo(@PathVariable("id") Long id) {
		return inquiryService.inquiryInfo(id);
	}
	
	@PutMapping("/{id:[0-9]+}")
	public Map<String, Object> inquiryInfoEdit(@PathVariable("id") Long id, @RequestBody Map<String, Object> inquiry){
		return inquiryService.inquiryInfoEdit(id, inquiry);
	}
	
}

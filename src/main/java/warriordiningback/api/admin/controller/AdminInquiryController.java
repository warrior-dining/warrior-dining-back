package warriordiningback.api.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import warriordiningback.api.admin.service.AdminInquiryService;
import warriordiningback.domain.Code;
import warriordiningback.domain.CodeRepository;
import warriordiningback.domain.inquiry.InquiriesAnswer;
import warriordiningback.domain.inquiry.InquiriesAnswerRepository;
import warriordiningback.domain.inquiry.Inquiry;
import warriordiningback.domain.inquiry.InquiryRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;


@Slf4j
@RequestMapping("/api/admin/inquiries")
@RestController
public class AdminInquiryController {

	@Autowired
	private InquiryRepository inquiryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private InquiriesAnswerRepository answerRepository;

	@Autowired
	private AdminInquiryService adminInquiryService;
	
	@Autowired
	private CodeRepository codeRepository;
	
	@GetMapping("/")
	public Map<String, Object> inquiryList(@RequestParam(name = "type", required = false) String searchType,
										   @RequestParam(name = "keyword", required = false) String searchKeyword, Pageable pageable) {
		return adminInquiryService.inquiryList(searchType, searchKeyword, pageable);
	}
	
	@GetMapping("/{id:[0-9]+}")
	public Map<String, Object> inquiryinfo(@PathVariable(name = "id") Long id){
		return adminInquiryService.inquiryInfo(id);
	}
	
	
	@PostMapping("/{id:[0-9]+}")
	public Map<String, Object> inquiryAnswerSave(@PathVariable("id") Long id, @RequestBody Map<String, Object> content) {
		return adminInquiryService.inquiryAnswerSave(id, content);
	}
}

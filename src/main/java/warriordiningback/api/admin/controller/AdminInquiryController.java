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
	private CodeRepository codeRepository;
	
	@GetMapping("/")
	public Map<String, Object> inquiry(Pageable pageable) {
		Map<String, Object> map = new HashMap<>();
		map.put("data", inquiryRepository.findAllByOrderByIdDesc(pageable));
		return map;
	}
	
	@GetMapping("/{id:[0-9]+}")
	public Map<String, Object> inquires(@PathVariable(name = "id") Long id){
		Map<String, Object> map = new HashMap<>();
		map.put("results", inquiryRepository.findById(id));
		return map;
	}
	
	
	@PostMapping("/{id:[0-9]+}")
	public Map<String, Object> inquiryAnswerSave(@PathVariable("id") Long id, @RequestBody Map<String, String> content) {
		log.info("호출완료");
		Map<String, Object> responseMap = new HashMap<>();
		// Content 값 넣기 해야되는데.....컨텐츠, 인쿼리 객체, 유저정보(답변을 작성하는 관리자 정보)
		Inquiry updateInquiryStatus = inquiryRepository.findById(id).orElseThrow(()-> new RuntimeException("uhuhu"));
		log.info("Inquriy : {}", updateInquiryStatus);			
		// 로그인기능 구현시 꼭!! findById 수정
		User user = userRepository.findById(1L).orElseThrow(()-> new RuntimeException("hhhh"));
		InquiriesAnswer saveAnswer = InquiriesAnswer.create(content.get("content"), updateInquiryStatus, user);
		saveAnswer = answerRepository.save(saveAnswer);
		
		//inquiry를 여기서 다시 불러서 상태값 처리됨으로 업데이트
		Code answerCode = codeRepository.findById(16L).orElseThrow(()-> new RuntimeException("해당코드 못찾음"));
		updateInquiryStatus.updateCode(updateInquiryStatus.getTitle(), updateInquiryStatus.getContent(), answerCode, updateInquiryStatus.getUser());
		inquiryRepository.save(updateInquiryStatus);
		responseMap.put("results", updateInquiryStatus);
		return responseMap;
	}
}

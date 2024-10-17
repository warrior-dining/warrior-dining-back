package warriordiningback.api.user.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import warriordiningback.api.user.dto.UserInquiryResponse;
import warriordiningback.domain.inquiry.Inquiry;
import warriordiningback.domain.inquiry.InquiryRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserInquiryServiceImp implements UserInquiryService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private InquiryRepository inquiryRepository;
	
	@Override
	public Map<String, Object> inquiryList(String email, Pageable pageable) {
		Map<String, Object> resultMap = new HashMap<>();
		Page<Inquiry> inquiry;
		User user = userRepository.findByEmail(email).orElseThrow(()-> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));
		inquiry = inquiryRepository.findByUser(user.getId(), pageable);
		
		resultMap.put("status", true);
		resultMap.put("results", inquiry);
		return resultMap;
	}

	@Override
	public Map<String, Object> inquiryInfo(Long inquiryId) {
		Map<String, Object> resultMap = new HashMap<>();
		Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(()-> new DiningApplicationException(ErrorCode.INQUIRY_INFO_NOT_FOUND));
		UserInquiryResponse inquiryResponse = new UserInquiryResponse();
		inquiryResponse.setId(inquiry.getId());
		inquiryResponse.setTitle(inquiry.getTitle());
		inquiryResponse.setContent(inquiry.getContent());
		
		resultMap.put("results", inquiryResponse);
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> inquiryInfoEdit(Long id, Map<String, Object> inquiry) {
		Map<String, Object> resultMap = new HashMap<>();
		Inquiry updateInquiry = inquiryRepository.findById(id).orElseThrow(()-> new DiningApplicationException(ErrorCode.INQUIRY_INFO_NOT_FOUND));
		log.info("inquiry : {}", inquiry);
		updateInquiry.inquiryUpdate(inquiry.get("title").toString(), inquiry.get("content").toString());
		
		resultMap.put("status", true);
		resultMap.put("results", updateInquiry);
		return resultMap;
	}

}

package com.example.animal.request;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AdoptionRequestController {
	private AdoptionRequestRepository adoptionRepo;
	private AdoptionRequestService service;

	@Autowired
	AdoptionRequestController(AdoptionRequestRepository adoptionRepo, AdoptionRequestService service) {
		this.adoptionRepo = adoptionRepo;
		this.service = service;
	}

	// 1건 추가
	@PostMapping(value = "/apply")
	public AdoptionRequest createApplication(@RequestBody AdoptionRequest request) {
		
		// 입양신청번호 생성
		String uniqueId = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Calendar date = Calendar.getInstance();
		uniqueId = sdf.format(date.getTime());
		
		uniqueId = uniqueId + RandomStringUtils.randomAlphanumeric(4);
		
		request.setRequestNo(uniqueId);
		System.out.println(request);
		
		adoptionRepo.save(request);
		service.sendApplication(request);
		return request;
	}

	// 1건 조회 (신청번호, 이름으로)
	@GetMapping(value = "/apply/search")
	public @ResponseBody List<AdoptionRequest> getApplication(@RequestParam("requestNo") long requestNo,
			@RequestParam("name") String name, HttpServletResponse res) {
		List<AdoptionRequest> app = adoptionRepo.findByRequestNoAndName(requestNo, name);

		if (app.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return app;
	}

	// 1건 조회 (id로)
	@GetMapping(value = "/apply/search/{id}")
	public @ResponseBody AdoptionRequest getApplicationById(@PathVariable("id") int id, HttpServletResponse res) {

		AdoptionRequest request = adoptionRepo.findById(id).orElse(null);

		if (request == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return request;
	}

	// 1건 수정
	@PatchMapping(value = "/apply/{id}")
	public AdoptionRequest editApplication(@PathVariable("id") int id, @RequestBody AdoptionRequest request,
			HttpServletResponse res) {

		AdoptionRequest app = adoptionRepo.findById(id).orElse(null);

		if (request == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		adoptionRepo.save(request);
		service.sendApplication(request);

		return app;
	}

	// 취소 요청
	@PatchMapping(value = "/cancel/{id}")
	public boolean cancelApplication(@PathVariable("id") int id, @RequestBody AdoptionRequest request, HttpServletResponse res) {
		
		AdoptionRequest app = adoptionRepo.findById(id).orElse(null);
		
		if (app == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}
		
		// 1. 입양취소 이유를 입양희망 컬럼에 저장		
		adoptionRepo.save(request);
		
		// 2. 관리자에게 메시지 전송
		service.deleteApplication(app);
		
		return true;
	}

}

package com.example.animal.request;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.animal.animal.AnimalRepository;

@RestController
public class AdoptionRequestController {
	private AdoptionRequestRepository adoptionRepo;
	private AnimalRepository animalRepo;
	private AdoptionRequestService service;
	
	@Autowired
	AdoptionRequestController(AdoptionRequestRepository adoptionRepo, AnimalRepository animalRepo, AdoptionRequestService service){
		this.adoptionRepo = adoptionRepo;
		this.animalRepo = animalRepo;
		this.service = service;
	}
	
	// 1건 추가
	@PostMapping(value="/apply")
	public AdoptionRequest createApplication(@RequestBody AdoptionRequest request) {
		adoptionRepo.save(request);
		return request;
	}
	
	// 1건 조회
	@GetMapping(value="/apply/search")
	public List<AdoptionRequest> getApplication(@RequestParam("requestNo") String requestNo, @RequestParam("name") String name, HttpServletResponse res) {
		return adoptionRepo.findByRequestNoAndName(requestNo, name);
		
	}
	
	@PutMapping(value="/apply/{id}")
	public boolean editApplication(@PathVariable("id") int id, @RequestBody AdoptionRequest request, HttpServletResponse res) {
		return true;
	}
	
	@PostMapping(value="/apply/{id}")
	public boolean cancelApplication(@PathVariable("id") int id) {
		return true;
	}
	
}

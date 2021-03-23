package com.example.animal.request;

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
	
	@PostMapping(value="/create-request")
	public boolean createApplication(AdoptionRequest request) {
		return true;
	}
	
	@GetMapping(value="/request/search")
	public AdoptionRequest getApplication(@RequestParam("requestNo") String requestNo, @RequestParam("name") String name) {
		return null;
	}
	
	@PutMapping(value="/request/{id}")
	public boolean editApplication(@PathVariable("id") int id, @RequestBody AdoptionRequest request, HttpServletResponse res) {
		return true;
	}
	
	@PostMapping(value="/request/{id}")
	public boolean cancelApplication(@PathVariable("id") int id) {
		return true;
	}
	
}

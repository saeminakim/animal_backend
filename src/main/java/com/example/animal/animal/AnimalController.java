package com.example.animal.animal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnimalController {

	private AnimalRepository animalRepo;

	@Autowired
	AnimalController(AnimalRepository animalRepo) {
		this.animalRepo = animalRepo;
	}

	// 목록 조회
	@GetMapping(value = "/animals")
	public Page<Animal> getAnimalList(@RequestParam("page") int page, @RequestParam("size") int size, HttpServletResponse res) {
		Page<Animal> animal = animalRepo.findAll(PageRequest.of(page, size, Sort.by("happenDt").descending()));
		
		if(animal.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return animal;
	}

	// id로 1건 조회
	@GetMapping(value = "/animals/{id}")
	public @ResponseBody Animal getAnimalDetails(@PathVariable long id, HttpServletResponse res) {
		Animal animal = animalRepo.findById(id).orElse(null);
		
		if(animal == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return animal;
	}

	// 시도 검색 
	@GetMapping(value = "/animals/filter/sido")
	public Page<Animal> getAnimalListBySido(@RequestParam("sido") String sido, @RequestParam("page") int page, @RequestParam("size") int size, HttpServletResponse res) {

		Page<Animal> animal = animalRepo.findBySido(sido, PageRequest.of(page, size, Sort.by("happenDt").descending()));
		
		if(animal.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return animal;
	}
	
	// 시도/구군 검색 
	@GetMapping(value = "/animals/filter/sido-gugun")
	public Page<Animal> getAnimalListBySidoAndGugun(@RequestParam("sido") String sido, @RequestParam("gugun") String gugun, @RequestParam("page") int page, @RequestParam("size") int size, HttpServletResponse res) {

		Page<Animal> animal = animalRepo.findBySidoAndGugun(sido, gugun, PageRequest.of(page, size, Sort.by("happenDt").descending()));
		
		if(animal.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return animal;
	}
	
	// 시도/축종 검색 
	@GetMapping(value = "/animals/filter/sido-type")
	public Page<Animal> getAnimalListBySidoAndType(@RequestParam("sido") String sido, @RequestParam("type") String type, @RequestParam("page") int page, @RequestParam("size") int size, HttpServletResponse res) {

		Page<Animal> animal = animalRepo.findBySidoAndType(sido, type, PageRequest.of(page, size, Sort.by("happenDt").descending()));
		
		if(animal.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return animal;
	}
	
	// 시도/구군/축종 검색 
	@GetMapping(value = "/animals/filter/sido-gugun-type")
	public Page<Animal> getAnimalListBySidoAndGugunAndType(@RequestParam("sido") String sido, @RequestParam("gugun") String gugun, @RequestParam("type") String type, @RequestParam("page") int page, @RequestParam("size") int size, HttpServletResponse res) {

		Page<Animal> animal = animalRepo.findBySidoAndGugunAndType(sido, gugun, type, PageRequest.of(page, size, Sort.by("happenDt").descending()));
		System.out.println("시도/구군/타입");
		System.out.println(animal);

		if(animal.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return animal;
	}
	
	// 시도/축종/상태 검색 
	@GetMapping(value = "/animals/filter/sido-type-status")
	public Page<Animal> getAnimalListBySidoAndTypeAndProcessStateContaining(@RequestParam("sido") String sido, @RequestParam("type") String type, @RequestParam("status") String status, @RequestParam("page") int page, @RequestParam("size") int size, HttpServletResponse res) {

		Page<Animal> animal = animalRepo.findBySidoAndTypeAndProcessStateContaining(sido, type, status, PageRequest.of(page, size, Sort.by("happenDt").descending()));
		
		if(animal.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return animal;
		
	}
	
	// 시도/구군/축종/상태 검색 
	@GetMapping(value = "/animals/filter/sido-gugun-type-status")
	public Page<Animal> getAnimalListBySidoAndGugunAndTypeAndStatusContaining(@RequestParam("sido") String sido, @RequestParam("gugun") String gugun, @RequestParam("type") String type, @RequestParam("status") String status, @RequestParam("page") int page, @RequestParam("size") int size, HttpServletResponse res) {

		Page<Animal> animal = animalRepo.findBySidoAndGugunAndTypeAndProcessStateContaining(sido, gugun, type, status, PageRequest.of(page, size, Sort.by("happenDt").descending()));

		if(animal.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		return animal;

	}
	
	// 축종 검색 
	@GetMapping(value = "/animals/filter/type")
	public Page<Animal> getAnimalListByType(@RequestParam("type") String type, @RequestParam("page") int page, @RequestParam("size") int size, HttpServletResponse res) {

		Page<Animal> animal = animalRepo.findByType(type, PageRequest.of(page, size, Sort.by("happenDt").descending()));
		
		if(animal.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return animal;
	}
	
	// 축종/상태 검색 
	@GetMapping(value = "/animals/filter/type-status")
	public Page<Animal> getAnimalListByTypeAndStatusContaining(@RequestParam("type") String type, @RequestParam("status") String status, @RequestParam("page") int page, @RequestParam("size") int size, HttpServletResponse res) {

		Page<Animal> animal = animalRepo.findByTypeAndProcessStateContaining(type, status, PageRequest.of(page, size, Sort.by("happenDt").descending()));
		
		if(animal.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return animal;
	}
	
	// 시도/상태 검색 
	@GetMapping(value = "/animals/filter/sido-status")
	public Page<Animal> getAnimalListBySidoAndStatusContaining(@RequestParam("sido") String sido, @RequestParam("status") String status, @RequestParam("page") int page, @RequestParam("size") int size, HttpServletResponse res) {

		Page<Animal> animal = animalRepo.findBySidoAndProcessStateContaining(sido, status, PageRequest.of(page, size, Sort.by("happenDt").descending()));
		
		if(animal.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return animal;
	}
	
	// 시도/구군/상태 검색 
	@GetMapping(value = "/animals/filter/sido-gugun-status")
	public Page<Animal> getAnimalListBySidoAndGugunAndStatusContaining(@RequestParam("sido") String sido, @RequestParam("gugun") String gugun, @RequestParam("status") String status, @RequestParam("page") int page, @RequestParam("size") int size, HttpServletResponse res) {

		Page<Animal> animal = animalRepo.findBySidoAndGugunAndProcessStateContaining(sido, gugun, status, PageRequest.of(page, size, Sort.by("happenDt").descending()));

		if(animal.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		return animal;
	}
	
	// 상태 검색 
	@GetMapping(value = "/animals/filter/status")
	public Page<Animal> getAnimalListByStatusContaining(@RequestParam("status") String status, @RequestParam("page") int page, @RequestParam("size") int size, HttpServletResponse res) {

		Page<Animal> animal = animalRepo.findByProcessStateContaining(status, PageRequest.of(page, size, Sort.by("happenDt").descending()));
		
		if(animal.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return animal;
	}

}

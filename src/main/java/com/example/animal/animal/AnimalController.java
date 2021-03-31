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
	public Page<Animal> getAnimalList(@RequestParam("page") int page, @RequestParam("size") int size) {
		return animalRepo.findAll(PageRequest.of(page, size, Sort.by("happenDt").descending()));
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
	@GetMapping(value = "/animals/filter")
	public Page<Animal> getAnimalListBySido(@RequestParam("sido") String sido, @RequestParam("page") int page, @RequestParam("size") int size) {

		return animalRepo.findBySido(sido, PageRequest.of(page, size, Sort.by("happenDt").descending()));
	}

}

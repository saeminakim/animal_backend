package com.example.animal.animal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

	@GetMapping(value = "/animals")
	public Page<Animal> getAnimalList(@RequestParam("page") int page, @RequestParam("size") int size) {
		return animalRepo.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
	}

//	@GetMapping(value = "/animals")
//	public List<Animal> getAnimalList(HttpServletRequest req) {
//		List<Animal> list = animalRepo.findAll(Sort.by("id").descending());
//		return list;
//	}

	@GetMapping(value = "/animals/{id}")
	public @ResponseBody Animal getAnimalDetails(@PathVariable long id, HttpServletResponse res) {
		return null;
	}

//	필터링하는 메소드 추가해야 함

}

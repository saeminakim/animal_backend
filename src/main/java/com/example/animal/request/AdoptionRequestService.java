package com.example.animal.request;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.animal.animal.Animal;
import com.example.animal.animal.AnimalRepository;

@Service
public class AdoptionRequestService {

	private RabbitTemplate rabbit;
	private AdoptionRequestRepository adoptionRepo;
	private AnimalRepository animalRepo;
	
	
	@Autowired
	public AdoptionRequestService(RabbitTemplate rabbit, AdoptionRequestRepository adoptionRepo, AnimalRepository animalRepo) {
		this.rabbit = rabbit;
		this.adoptionRepo = adoptionRepo;
		this.animalRepo = animalRepo;
	}
	
	// 새 입양신청서 관리자에게 전송 
	public void sendApplication(AdoptionRequest request) {
		System.out.println("-----SEND APPLICATION-----");
		System.out.println(request);
		
		try {
			rabbit.convertAndSend("adoption.request", request);
			rabbit.convertAndSend("basic.request", request);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void updateApplication(AdoptionRequest request) {
		System.out.println("-----UPDATE APPLICATION-----");
	}
	
	// 취소 요청 관리자에게 전송
	public void cancelApplication(AdoptionRequest request) {
		System.out.println("-----CANCEL APPLICATION-----");
		
		try {
			rabbit.convertAndSend("adoption.request", request);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// 보낸 메시지 받아주는 메소드
	@RabbitListener(queues = "manager.application.status")
	public void receiveApplication(AdoptionRequest request) {
		System.out.println("---- Manager LOG (받은메시지) -----");
		System.out.println(request);
		
		AdoptionRequest adoptionRequest = AdoptionRequest.builder()
				.id(request.getId())
				.requestNo(request.getRequestNo())
				.animalId(request.getAnimalId())
				.noticeNo(request.getNoticeNo())
				.name(request.getName())
				.mobile(request.getMobile())
				.email(request.getEmail())
				.gender(request.getGender())
				.address(request.getAddress())
				.job(request.getJob())
				.familyMember(request.getFamilyMember())
				.familyAgreed(request.getFamilyAgreed())
				.petAtHome(request.getPetAtHome())
				.petDetails(request.getPetDetails())
				.houseType(request.getHouseType())
				.reason(request.getReason())
				.status(request.getStatus())
				.animalImg(request.getAnimalImg())
				.build();
		
		
		// 해당 동물의 상태를 관리자가 넘겨준 상태로 변경
		long animalId = request.getAnimalId();
		Animal animal = animalRepo.findById(animalId).orElse(null);
		
		String newStatus = request.getStatus();
		
		animal.setProcessState(newStatus);
		animalRepo.save(animal);
		
		adoptionRepo.save(adoptionRequest);
		
		
	}
}

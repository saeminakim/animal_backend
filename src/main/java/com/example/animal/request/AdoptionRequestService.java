package com.example.animal.request;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
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
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
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
	
	// 새 입양신청서 후기에 전송 
//	public void sendToReview(AdoptionRequest request) {
//		System.out.println("-----SEND TO REVIEW -----");
//		System.out.println(request);
//		
//		try {
//			rabbit.convertAndSend("adoption.status", request);
//			rabbit.convertAndSend("adoption.status", animal);
//		} catch(Exception e) {
//			System.out.println(e.getMessage());
//		}
//		
//	}
	
	// 보낸 메시지 받아주는 메소드
//	(queues = "manager.application.status")
	@RabbitListener(bindings = {
			@QueueBinding(exchange = @Exchange(name = "amq.topic", type = "topic"), value = @Queue(value = "manager.application.status"), key = { "adoption" }) })
	public void receiveApplication(AdoptionRequest request) {
		System.out.println("---- Manager LOG (받은메시지) -----");
		System.out.println(request);
		
		AdoptionRequest adoptionRequest = AdoptionRequest.builder()
				.id(request.getAdoptionId())
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

		
		System.out.println(adoptionRequest);
		adoptionRepo.save(adoptionRequest);
		
		// 해당 동물의 상태를 관리자가 넘겨준 상태로 변경
		long animalId = request.getAnimalId();
		System.out.println(animalId);
		
		
		Animal animal = animalRepo.findById(animalId).orElse(null);
		System.out.println(animal);
		
		String newStatus = request.getStatus();
		
		if(newStatus.contains("취소") || newStatus.contains("거절")) {
			animal.setProcessState("보호중");
			animalRepo.save(animal);			
		} else {
			animal.setProcessState("입양완료");
			animalRepo.save(animal);
			System.out.println(animal);			
		}
		
	}
}

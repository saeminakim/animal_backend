package com.example.animal.request;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdoptionRequestService {

	private RabbitTemplate rabbit;
	private AdoptionRequestRepository adoptionRepo;
	
	
	@Autowired
	public AdoptionRequestService(RabbitTemplate rabbit, AdoptionRequestRepository adoptionRepo) {
		this.rabbit = rabbit;
		this.adoptionRepo = adoptionRepo;
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
		adoptionRepo.save(request);
	}
}

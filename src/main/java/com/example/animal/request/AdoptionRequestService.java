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
	
	public void deleteApplication(AdoptionRequest request) {
		System.out.println("-----DELETE APPLICATION-----");
	}
	
	@RabbitListener(queues = "manager.application.status")
	public void receiveApplication(AdoptionRequest request) {
		System.out.println("---- Manager LOG -----");
		System.out.println(request);
	}
}

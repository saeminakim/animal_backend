package com.example.animal.request;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdoptionRequestService {

	private RabbitTemplate rabbit;
	
	@Autowired
	public AdoptionRequestService(RabbitTemplate rabbit) {
		this.rabbit = rabbit;
	}
	
	public void sendApplication(AdoptionRequest request) {
		System.out.println("-----SEND APPLICATION-----");
	}
	
	public void updateApplication(AdoptionRequest request) {
		System.out.println("-----UPDATE APPLICATION-----");
	}
	
	public void deleteApplication(AdoptionRequest request) {
		System.out.println("-----DELETE APPLICATION-----");
	}
}

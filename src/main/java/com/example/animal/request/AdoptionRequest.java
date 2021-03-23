package com.example.animal.request;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AdoptionRequest {
	@Id
	private int id;
	private String requestNo;
	private int animalId;
	private String noticeNo;
	private String name;
	private String mobile;
	private String email;
	private String gender;
	private String address;
	private String job;
	private String familyMember;
	private boolean familyAgreed;
	private boolean petAtHome;
	private String petDetails;
	private String houseType;
	private String reason;
	private String status;
	
}

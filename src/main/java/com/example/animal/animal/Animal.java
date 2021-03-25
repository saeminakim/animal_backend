package com.example.animal.animal;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.animal.request.AdoptionRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Animal {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String kindCd;
	private String type;
	private String kind;
	private String sexCd;
	private String age;
	private String popfile;
	private String weight;
	private String noticeNo;
	private String careNm;
	private String careTel;
	private String chargeNm;
	private String officetel;
	private String specialMark;
	private String orgNm;
	private String sido;
	private String gugun;
	private String happenPlace;
	private long happenDt;
	private long noticeSdt;
	private long noticeEdt;
	private String processState;
	
	public Animal(AnimalResponse.ResponseItem res) {
		
		this.kindCd = res.getKindCd();
		this.sexCd = res.getSexCd();
		this.age = res.getAge();
		this.popfile = res.getPopfile();
		this.weight = res.getWeight();
		this.noticeNo = res.getNoticeNo();
		this.careNm = res.getCareNm();
		this.careTel = res.getCareTel();
		this.chargeNm = res.getChargeNm();
		this.officetel = res.getOfficetel();
		this.specialMark = res.getSpecialMark();
		this.orgNm = res.getOrgNm();
		this.happenPlace = res.getHappenPlace();
		this.happenDt = res.getHappenDt();
		this.noticeSdt = res.getNoticeSdt();
		this.noticeEdt = res.getNoticeEdt();
		this.processState = res.getProcessState();

	}

	@OneToMany
	private List<AdoptionRequest> registeredRequest;
	
}

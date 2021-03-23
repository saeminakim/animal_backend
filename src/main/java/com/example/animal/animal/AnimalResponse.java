package com.example.animal.animal;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class AnimalResponse {
	private ResponseData response;

	@Data @NoArgsConstructor
	class ResponseData {
		private ResponseBody body;
	}
	
	@Data @NoArgsConstructor
	class ResponseBody {
		private int totalCount;
		private ResponseItems items;
	}
	
	@Data @NoArgsConstructor
	class ResponseItems {
		private List<ResponseItem> item;
	}
	
	@Data @NoArgsConstructor
	class ResponseItem {
		private String kindCd;
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
		private String happenPlace;
		private long happenDt;
		private long noticeSdt;
		private long noticeEdt;
		private String processState;
	}
}

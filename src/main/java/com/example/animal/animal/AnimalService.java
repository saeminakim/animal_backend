package com.example.animal.animal;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class AnimalService {

	private String serviceKey = "IhgvagJ8Tmo5EmpS4azcLIGde2OhTHpsNrp6n%2FbrfXYN9GEU7bYRzJZn3tbe2VOEY8k1mPGZGfVFfXzbQdpGHA%3D%3D";
	private AnimalRepository animalRepo;

	@Autowired
	public AnimalService(AnimalRepository animalRepo) {
		this.animalRepo = animalRepo;
	}

//	@Scheduled(fixedRate = 1000 * 60 * 30)
	@Scheduled(cron = "0 0 10 * * *") // 매일 오전 10시 실행
	public void requestAnimalHourlyData() throws IOException {

		// 현재 날짜, 한달 전 날짜 구한 후 getAnimal()의 매개변수로 넘겨주며 호출
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String today = df.format(cal.getTime());

		cal.add(Calendar.MONTH, -1);

		String monthAgo = df.format(cal.getTime());

		getAnimal(today, monthAgo);
	}

	private void getAnimal(String today, String monthAgo) throws IOException {

		int page = 1;
		int totalCount = 0;

		while (true) {

			// 1. URL 만들기 및 URL 객체 생성
			StringBuilder builder = new StringBuilder();
			builder.append("http://openapi.animal.go.kr/openapi/service/rest");
			builder.append("/abandonmentPublicSrvc/abandonmentPublic");
			builder.append("?ServiceKey=" + serviceKey);
			builder.append("&bgnde=" + monthAgo);
			builder.append("&endde=" + today);
			builder.append("&pageNo=" + page);
			builder.append("&numOfRows=100");
			// builder.append("&pageNo=1&numOfRows=10");
			// builder.append("&numOfRows=30");

			URL url = new URL(builder.toString());

			// 2. 커넥션 생성
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// 3. 데이터 읽어오기. 스트림 읽어서 리더 객체로 변환
			byte[] result = con.getInputStream().readAllBytes();

			// 4. 문자열로 변환
			String data = new String(result);
			
//			SAXParserFactory factory = SAXParserFactory.newInstance();
//			try {
//				SAXParser parser = factory.newSAXParser();
//				AnimalHandler handler = new AnimalHandler();
//				parser.parse(data, handler);
//				
//				List<Animal> list = handler.getAnimalList();
//				System.out.println("------리스트------");
//				System.out.println(list);
//				
//				for(Animal a : list) {
//					System.out.println("동물 정보 : " + a);
//				}
//				
//				
//
//				
//			} catch (ParserConfigurationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SAXException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//
			// 5. XML -> JSON 변환
			JSONObject json = XML.toJSONObject(data);


			String jsonPrettyPrintString = json.toString(4);

			// 6. JSON -> POJO 변환
			AnimalResponse res = new Gson().fromJson(jsonPrettyPrintString, AnimalResponse.class);

			// 전체 공고 수 
			totalCount = res.getResponse().getBody().getTotalCount();

			// 7. 응답 데이터 가공

			for (AnimalResponse.ResponseItem item : res.getResponse().getBody().getItems().getItem()) {

				Animal animal = new Animal(item);

				// 품종에서 축종, 상세품종을 분류
				String type = animal.getKindCd();
				type = type.replace("[", "");
				type = type.replace("]", "");
				String[] typeArray = type.split(" ");
				animal.setType(typeArray[0]);

				String kind = type.substring(type.indexOf(" ") + 1);
				animal.setKind(kind);

				// 관할 기관에서 시도, 구군을 분류
				String sido = animal.getOrgNm();
				String[] sidoArray2 = sido.split(" ");
				animal.setSido(sidoArray2[0]);

				String gugun = sido.substring(sido.indexOf(" ") + 1);
				animal.setGugun(gugun);
				
				// 성별 한글로 변환
				String gender = animal.getSexCd();
				if(gender.equals("M")) {
					animal.setSexCd("남");
				} else if(gender.equals("F")) {
					animal.setSexCd("여");
				} else {
					animal.setSexCd("미상");
				}

				// 종료가 아닌 모든 상태의 유기동물정보 저장
				if (!animal.getProcessState().contains("종료")) {

					// DB의 공고번호와 새로 가져온 애니멀 객체의 공고번호 비교 후 DB에 없다면 저장
					Animal savedAnimal = animalRepo.findByNoticeNo(animal.getNoticeNo());
					if (savedAnimal == null) {
						animalRepo.save(animal);
					}
				}
			}

//			 총 공고 페이지 수만큼 반복문 실행
			page += 1;
			double pageDouble = (double)totalCount / 100;
			int pageCount = (int)Math.round(pageDouble);
			System.out.println("page : " + page);
			System.out.println("pageCount : " + pageCount);
			if (page > pageCount) {
				break;
			}
		}

	}
	
	
}
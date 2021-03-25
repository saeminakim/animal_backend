package com.example.animal.animal;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.animal.animal.AnimalResponse.ResponseItem;
import com.google.gson.Gson;

@Service
public class AnimalService {

	private String serviceKey = "y62vvVnWf5WvQNEv9RAklWtI21VtI2QjebPFXKg6yYEsDk13Ujkfz0LS5Nb%2FaGcN%2BXbzLed6pJWi2CQKXDNYag%3D%3D";
	private AnimalRepository animalRepo;

	@Autowired
	public AnimalService(AnimalRepository animalRepo) {
		this.animalRepo = animalRepo;
	}

//	@Scheduled(fixedRate = 1000 * 60 * 30)
	@Scheduled(cron = "0 0 * * * *")
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
			// builder.append("&pageNo=1&numOfRows=10");
			// builder.append("&numOfRows=30");

			URL url = new URL(builder.toString());

			// 2. 커넥션 생성
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// 3. 데이터 읽어오기. 스트림 읽어서 리더 객체로 변환
			byte[] result = con.getInputStream().readAllBytes();

			// 4. 문자열로 변환
			String data = new String(result);
			System.out.println("----data----");
			System.out.println(data);
//
//			String xmlString = data.toString();
//			System.out.println("----xmlString----");
//			System.out.println(xmlString);

			// 5. XML -> JSON 변환
			JSONObject json = XML.toJSONObject(data);


			String jsonPrettyPrintString = json.toString(4);

			// 6. JSON -> POJO 변환
			AnimalResponse res = new Gson().fromJson(jsonPrettyPrintString, AnimalResponse.class);

			totalCount = res.getResponse().getBody().getTotalCount();
//	        System.out.println("----totalCount----");
//	        System.out.println(totalCount);

			// 7. 응답 데이터 가공
			List<ResponseItem> list = res.getResponse().getBody().getItems().getItem();
			Collections.reverse(list);
			for (AnimalResponse.ResponseItem item : list) {

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

				// 종료가 아닌 모든 상태의 유기동물정보 저장
				if (!animal.getProcessState().contains("종료")) {

					// DB의 공고번호와 새로 가져온 애니멀 객체의 공고번호 비교 후 DB에 없다면 저장
					Animal savedAnimal = animalRepo.findByNoticeNo(animal.getNoticeNo());
					if (savedAnimal == null) {
						animalRepo.save(animal);
					}
				}
			}

			// 총 공고 페이지 수만큼 반복문 실행
			page += 1;
			System.out.println("페이지 수 : " + page);
			System.out.println("totalCount : " + totalCount);
			if (page > totalCount / 10) {
				break;
			}
		}

	}
}
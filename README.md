# 멍냥멍냥
<img src= "https://user-images.githubusercontent.com/78783995/115801226-31b3c480-a417-11eb-8aad-fa95dd0abccd.png" width="400">


## 프로젝트 소개
코로나 19 여파로 반려동물 입양/분양이 증가했지만 유기동물 수 역시 전년 동기 대비 6배 증가하였습니다. <br>
이에 유기동물에게 새로운 가족을 찾아줄 수 있는 분양 웹서비스를 기획하였습니다. 
<br><br>

## 담당 기능
1. 유기동물 목록
  * 공공데이터 오픈 API를 활용한 유기동물 목록 조회
  * 지역, 축종, 입양상태로 유기동물 목록 필터링 기능
  * 목록 페이징 처리

2. 유기동물 입양신청서
  * 입양신청서 작성, 조회, 수정, 취소요청 기능
  * OAuth2 방식의 카카오 로그인
  * 세션정보를 Redis에 저장
  * 입양신청서 작성 후 RabbitMQ를 통해 관리자에게 입양신청서 전송 
  
<br><br>
## 사용 기술
1. Framework : Vue.js, Spring Boot
2. 사용 언어 : Java, Javascript
3. Cloud : AWS EC2, S3
4. Middleware : RabbitMQ
5. Database : MySQL, Redis
6. CI/CD : Jenkins
7. VCS : Git SCM, GitHub
8. Tool : Visual Studio Code, Spring STS

<br><br>
## 구현 이미지
 * 유기동물 목록 <br>
![main](https://user-images.githubusercontent.com/78783995/115802465-e18a3180-a419-11eb-9e35-d2e7c261fdd6.png) <br>

* 입양신청서 조회<br>
<img src="https://user-images.githubusercontent.com/78783995/115803174-54e07300-a41b-11eb-981a-2e0809b6e0bd.png" width=700>



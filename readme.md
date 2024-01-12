# FISA JAVA MINI-PROJECT 

DATE : 2024-01-12   
MEMBERS : 백성욱, 윤종욱, 우준희, GPT3.5


## INTRO 
교육에서 배운 JAVA 문법과 관련되어 자유 주제를 선정하여 JAVA MINI-PROJECT를 수행했습니다.

## DATAFLOW  

![dataflow](./image/dataflow_edit.png)

## OBJECTS

|FILE|METHOD|ROLES|
|--|--|--|
|run_main.java|main|작업 실행|
|webCrawling.py|webCrawling|메뉴 데이터 크롤링|
|webCrawling.java|removeHtmlTags|HTML 파싱|
|webCrawling.java|extractTextBetweenWords|Text 파싱|
|webCrawling.java|parseWeatherInfo|Data 파싱|
|webCrawling.java|Weather|Data 재정렬|
|telebot.java|funcTelegram|Telebot 메세징|
|scheduler.java|scheduler|Task 스케줄링|
|filescanner.java|filescanner|txt 파일 스캐닝|
|executePython.java|executePython|파이썬 파일 실행자|














## TROUBLESHOOTING
1. 백성욱
- 데이터 입수 : 날씨 관련된 API를 사용하려 했으나, API 승인 시간, 유료 결제 등으로 데이터 입수에 어려움을 겪음.
- Spring Boot 필요성 : 대부분의 크롤링 기술은 Maven, Gradle 등 자동화빌더,의존성 도구가 필요했지만, 해당 도구에 대해 활용법을 알지 못해서 사용하지 못함.

2. 윤종욱
- 데이터 입수 : 중앙그룹 중식 식단을 API를 통해 추출하려고 하였으나, 관련 API를 제공하지 않는 관계로 공공데이터셋인 급식 식단 API를 사용
- json으로 받고 txt 파일로 변환하는 것을 파이썬에서는 제대로 실행되었지만, 자바에서 실행했을 때 경로 문제로 py가 실행되지 않거나, output 파일의 위치가 예상과는 다른 곳에 생성되는 문제 발생
- 해당 문제는 파이썬 실행 중 실행하는 디렉토리 값을 os.getcwd()를 통해 가져오고, 파일을 저장할 디렉토리를 새로 생성하는 것으로 해결

3. 우준희
- JDBC도 활용하려고 했으나, Springboot 요구
- 초기 SlackBot을 활용하려고 했으나, Springboot 요구
- 다양한 WebCrawling을 구현하려고 했으나, 다양한 Springboot 요구
- 맨 처음 프로젝트에 대한 브레인스토밍에서 다양한 아이디어가 나왔으나, 대부분 gradle, maven에 대한 지식과 Springboot 요구
  
<!--  
처음에 날씨 관련 API를 사용하여 했으나, 기상청 API는 담당자의 승인 시간이 필요하고, 네이버와 카카오는 API를 제공하지 않으며, 그 외의 날씨예보를 전해주는 기관은 API를 유료로 제공하는 등 API에 대한 접근성이 좋지 않았음.
이에 Naver에 "오늘 날씨"를 검색했을 때 나오는 정보를 Crawling하여, 필요한 텍스트 정보를 뽑아내어 사용자에게 당일 날씨에 대한 정보를 제공하기로 함.
처음에 html로 작성된 모든 텍스트 정보를 긁어온 다음, <>로 둘러쌓인 태그를 정규식을 사용해 제거해였고, 특정 단어들 사이에 존재하는 핵심 정보를 가져와 보기 좋은 String 형식으로 변환함.
위의 모든 과정은 WeatherCrawling.java에 작성하였으며, 해당 파일의 Weather() 함수를 사용하게 된다면 최종적으로 사용자에게 보여줄 형식의 날씨 정보만 return하는 형식으로 구성함.
대부분은 크롤링을 Spring framework를 사용하여 진행하였지만, Maven, Gradle등의 개념이 부족하여 흔히 하는 방식으로 진행하지 못한게 어려웠음.-->


<!--  
<식단 관련>
처음 목표는 중앙그룹 중식 식단을 API를 통해 추출하려고 하였으나, 관련 API를 제공하지 않는 관계로 공공데이터셋인 급식 식단 API를 사용하게 되었다.
이번 주의 식단을 json으로 받고 txt 파일로 변환하는 것을 파이썬에서는 제대로 실행되었지만, 자바에서 실행했을 때 경로 문제로 py가 실행되지 않거나, output 파일의 위치가 예상과는 다른 곳에 생성되는 문제가 있었다.
해당 문제는 파이썬 실행 중 실행하는 디렉토리 값을 os.getcwd()를 통해 가져오고, 파일을 저장할 디렉토리를 새로 생성하는 것으로 해결할 수 있었다.

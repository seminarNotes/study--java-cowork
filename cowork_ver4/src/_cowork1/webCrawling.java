package _cowork1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class webCrawling { 
	
	public static String removeHtmlTags(String html) {
		// 정규표현식 패턴
		String regex = "<[^>]*>";
		Pattern pattern = Pattern.compile(regex);

		// 정규표현식과 매치되는 부분 제거
		Matcher matcher = pattern.matcher(html);
		String textOnly = matcher.replaceAll("");

		return textOnly;
	}
	
	public static String extractTextBetweenWords(String text, String startWord, String endWord) {
		// 시작 단어와 끝 단어를 기반으로 정규표현식 패턴 생성
		// startWord, endWord는 포함 안됨
		String regex = startWord + "(.*?)" + endWord;
		Pattern pattern = Pattern.compile(regex);

		// 정규표현식과 매치되는 부분 추출
		Matcher matcher = pattern.matcher(text);

		// 매치된 부분이 있으면 추출된 텍스트 반환, 없으면 빈 문자열 반환
		return matcher.find() ? matcher.group(1).trim() : "";
	}
	
	public static String parseWeatherInfo(String input) {
		// 보기 좋게 String 구성
        StringBuilder result = new StringBuilder();
        // 주어진 문자열을 줄바꿈 문자("\n")로 분할
        String[] lines = input.split("\\s+");
        // 필요한 정보 추출 및 형식화
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            
            switch (line) {
                case "오늘의":
                    result.append("오늘의 날씨 : ").append(lines[i + 2]).append("\n");
                    break;
                case "현재":
                    result.append("현재 온도 : ").append(lines[i + 1].split("온도")[1]).append("\n");
                    break;
                case "체감":
                    result.append("체감 : ").append(lines[i + 1]).append("\n");
                    break;
                case "습도":
                    result.append("습도 : ").append(lines[i + 1]).append("\n");
                    break;
                case "미세먼지":
                    result.append("미세먼지 : ").append(lines[i + 1]).append("\n");
                    break;
                case "초미세먼지":
                    result.append("초메시먼지 : ").append(lines[i + 1]).append("\n");
                    break;
                case "자외선":
                    result.append("자외선 : ").append(lines[i + 1]).append("\n");
                    break;
                default:
                    break;
            }
        }
        return result.toString();
    }

	public String Weather() throws IOException{
		String finalWeather = "";
		
		try {
			// 웹 페이지 URL : 네이버에 "오늘 날씨"를 검색했을 때 나오는 화면에서 정보 가져옴
			String url = JavaConfidential.NAVER_WEATHER_URL;
			
			// 최대 5번까지 리다이렉션을 따라감 - 오류 발생 막기 위함
			int maxRedirects = 5;
			int redirects = 0;

			while (redirects < maxRedirects) {
				// URL 객체 생성
				URL obj = new URL(url);

				// HTTP 연결 설정
				HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

				// 요청 메소드 설정
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(5000); // 5초 연결 타임아웃
				connection.setReadTimeout(5000);    // 5초 읽기 타임아웃

				// 응답 코드 확인
				int responseCode = connection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					// 응답이 성공하면 BufferedReader를 사용하여 HTML 읽기
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String inputLine;
					StringBuilder content = new StringBuilder();

					while ((inputLine = in.readLine()) != null) {
						content.append(inputLine);
					}
					in.close();

					// HTML 출력
					String s = content.toString();
					// 정규표현식을 사용하여 HTML 태그 제거
					String textOnly = removeHtmlTags(s);
					// 예보 비교 ~ 일몰 사이의 필요한 부분 추출
					String TodayWeather = extractTextBetweenWords(textOnly, "예보비교", "일몰");

					// 현재 날짜 구하기        
					LocalDate now = LocalDate.now();        
					// 포맷 정의        
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");        
					// 포맷 적용        
					String formatedNow = now.format(formatter);
					// 보기 좋게 딕셔너리형태의 String으로 출력
					finalWeather = "["+formatedNow+"]"+"[날씨]\n" + parseWeatherInfo(TodayWeather);
					System.out.println("디버깅");
					System.out.println(parseWeatherInfo(TodayWeather));
			        // 결과 출력
			        return finalWeather;
					// 결과 출력
			        // System.out.println(TodayWeather);

				} else if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
					// 리다이렉션일 경우 새로운 URL로 설정
					url = connection.getHeaderField("Location");
					redirects++;

				} else {
					System.out.println("HTTP GET 요청 실패: " + responseCode);
					break;  // 다른 오류가 발생하면 반복문 종료
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return finalWeather;
	} 
}
package _cowork1;

//import java.io.IOException;
import java.sql.SQLException;
//import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class run_main {
    public static void main(String[] args) throws Exception {
        // [값 선언] 텔레그램 토큰(BOT ID)
        String token = JavaConfidential.TOKEN;
        // [값 선언] 텔레그램 채팅ID
        String chatId = JavaConfidential.CHAT_ID;
        // [값 선언] 파일 위치
//        String filePath = "C:\\ITStudy\\04_java\\cowork\\data\\output.txt";
        // [값 선언] 파일 인코딩
//        String encoding = "UTF-8";
        // [값 선언] 파일 내용 초기화
        String message1 = "";
        String message2 = "";
        String weatherMessage = "";
        String stockMessage = "";
        System.out.println("[INFO] 값 선언 완료");

        // [객체 생성] 파이썬 실행 객체 생성
        executePython executor = new executePython();
        System.out.println("[INFO] 파이썬 실행 객체 생성 완료");

        // [객체 생성] 웹 크롤링 객체 생성
        webCrawling crawler = new webCrawling();
        System.out.println("[INFO] 웹 크롤링 객체 생성 완료"); 
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("[SYS] 수행하고자 하는 작업의 번호를 선택하세요 : ");
        System.out.println("[SYS] 1. 날씨 정보 출력");
        System.out.println("[SYS] 2. 메뉴 정보 출력");
        System.out.println("[SYS] 4. 날씨 정보 입력");
        System.out.println("[SYS] 5. 메뉴 정보 입력");
        System.out.println("[SYS] 9. 현재 주가 정보 확인");
        System.out.println("[SYS] 0. 시스템 종료");
        
        while (true) {
        	int number = scanner.nextInt();
        	
        	if (number == 1) {
        		System.out.println("날씨 정보 메세징 함수");
        		try {             
                	// 현재 날짜 구하기        
        			LocalDate now = LocalDate.now();                
        			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");        
        			String formatedNow = now.format(formatter); // 2024/01/16
        			
        			// DB에서 정보 가져오기
        			weatherMessage = DataControllerWeather.select(formatedNow);   			                    
                } catch (Exception e) {
                    e.printStackTrace();
                }

                telebot.funcTelegram(token, chatId, weatherMessage);
        	}        	   	
        	else if (number == 2) {
        		System.out.println("메뉴 정보 메세징 함수");
                try {
					message1 = GetMenuFromDB.getMenuFromDB();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
                telebot.funcTelegram(token, chatId, message1);       		
        	}
        	else if (number == 4) {
        		System.out.println("날씨 정보 DB저장 함수");
        		try {
        	          message2 = crawler.Weather();          
        	          DataControllerWeather dcw = new DataControllerWeather(message2);
        	          DataControllerWeather.insert(
        	          								dcw.BASEDATE, 
        							            	dcw.CONDITION_, 
        							            	dcw.TEMPERATURE_R, 
        							            	dcw.TEMPERATURE_F,
        							            	dcw.HUMIDITY,
        							            	dcw.DUST_F,
        							            	dcw.DUST_UF,
        							            	dcw.UV
        							            	);		
        	      } catch (Exception e) {
        	          e.printStackTrace();
        	      }      		
        	}        	
        	else if (number == 5) {
        		System.out.println("메뉴 정보 DB저장 함수");
        		executor.Run();
        	}
        	else if (number == 9) {
        		System.out.println("주가 정보 확인");
        		try {
                    // 파이썬 스크립트 경로
                    String pythonScriptPath = "\"C:\\ITStudy\\04_java\\cowork\\src\\_cowork1\\stock_price.py\"";

                    // 명령어를 문자열로 구성
                    String command = "python " + pythonScriptPath;

                    // 외부 프로그램 실행
                    Process process = Runtime.getRuntime().exec(command);

                    // 프로세스 실행 완료 대기
                    int exitCode = process.waitFor();

                    if (exitCode == 0) {
                        // 파이썬 스크립트의 출력을 가져오기
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        StringBuilder output = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            output.append(line).append("\n");
                        }

                        // 출력 결과 출력
                        stockMessage = output.toString();
                    } else {
                        // 오류가 있을 경우 오류 코드 출력
                        System.out.println("파이썬 스크립트 실행 중 오류 발생. 오류 코드: " + exitCode);
                    }

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
        		telebot.funcTelegram(token, chatId, stockMessage);
        	}
        	else if (number == 0) {
        		System.out.println("[SYS] 시스템 종료");
        		System.exit(0);
        		break;
        	}        	
        	else {
                System.out.println("[SYS][WARN] 유효하지 않은 입력값");
                continue; // 입력이 잘못되었을 경우 다시 반복
        	}       	
        }
        scanner.close();
        


        
    
        
        // // [객체 생성] 스케줄러 객체 생성
        // scheduler taskScheduler = new scheduler();
        // System.out.println("[INFO] 스케줄러 객체 생성 완료");

    
        // // 실행 시간을 설정하고 함수 호출을 스케줄링
        // taskScheduler.execute(() -> {
        //     telebot.funcTelegram(token, chatId, "test1");
        // }, 16, 50, 00);

        // // 실행 시간을 설정하고 함수 호출을 스케줄링
        // taskScheduler.execute(() -> {   
        //     telebot.funcTelegram(token, chatId, "test2");
        // }, 17, 10, 00);
    }
}

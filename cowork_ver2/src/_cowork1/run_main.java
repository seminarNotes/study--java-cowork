package _cowork1;

import java.io.IOException;

public class run_main {
    public static void main(String[] args) {
        // [값 선언] 텔레그램 토큰(BOT ID)
        String token = "NULL";
        // [값 선언] 텔레그램 채팅ID
        String chatId = "NULL";
        // [값 선언] 파일 위치
        String filePath = "NULL";
        // [값 선언] 파일 인코딩
        String encoding = "UTF-8";
        // [값 선언] 파일 내용 초기화
        String message1 = "";
        String message2 = "";
        System.out.println("[INFO] 값 선언 완료");

        // [객체 생성] 파일 스캐너 객체 생성
        filescanner txtscanner = new filescanner();
        System.out.println("[INFO] 파일 스캐너 객체 생성 완료");

        // [객체 생성] 텔레봇 객체 생성
        telebot.funcTelegram(token, chatId, message1);
        System.out.println("[INFO] 텔레봇 객체 생성 완료");

        // [객체 생성] 파이썬 실행 객체 생성
        executePython executor = new executePython();
        System.out.println("[INFO] 파이썬 실행 객체 생성 완료");

        // [객체 생성] 웹 크롤링 객체 생성
        webCrawling crawler = new webCrawling();
        System.out.println("[INFO] 웹 크롤링 객체 생성 완료");



        // 동작1 : 파이썬 실행
        executor.Run();

        // 동작2 : 데이터 local -> java
        try {
            message1 = txtscanner.readFileToString(filePath, encoding);
            System.out.println("파일 내용:\n" + message1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        telebot.funcTelegram(token, chatId, message1);

        // 동작3 : 날씨 정보 
        try {
            message2 = crawler.Weather();
        } catch (IOException e) {
            e.printStackTrace();
        }
        telebot.funcTelegram(token, chatId, message2);
        // System.out.println("파일 내용:\n" + message2);

        

        
    
        
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

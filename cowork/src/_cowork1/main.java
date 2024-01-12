package _cowork1;

public class main {
    public static void main(String[] args) {
        String token = "NULL";
        String chatId = "NULL";
        String message = "실행 오류";
		try {
			message = WeatherCrawling.Weather();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        telebot.funcTelegram(token, chatId, message);



        
    }
}

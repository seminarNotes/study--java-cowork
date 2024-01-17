package _cowork1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class telebot {
    public static void funcTelegram(String token, String chatId, String message) {
        BufferedReader in = null;

        try {
            String encodedMessage = URLEncoder.encode(message, "UTF-8");
            URL obj = new URL("https://api.telegram.org/bot" + token + "/sendMessage?chat_id=" + chatId + "&text=" + encodedMessage);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String line;

            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) try { in.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}

package _cowork1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class GetMenuFromDB {
    public static String getMenuFromDB() throws SQLException, ClassNotFoundException {
        String message = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        LocalDate specificDate = LocalDate.of(2024, 1, 12);

        // 해당 날짜의 주의 시작일(월요일)
        LocalDate mondayOfTheWeek = specificDate.with(DayOfWeek.MONDAY);
        Date date = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://woori-fisa2.cfnz7hfzq9bn.ap-northeast-2.rds.amazonaws.com/I_HATE_JAVA?characterEncoding=UTF-8&serverTimezone=UTC";
			String id = "admin";
			String pw = "woorifisa2!";

            // db접속 객체
            conn = DriverManager.getConnection(url, id, pw);

            pstmt = conn.prepareStatement("SELECT * FROM TABLE_MENU WHERE BASEDATE = ?");
            for (int i = 0; i < 5; i++) {
                date = java.sql.Date.valueOf(mondayOfTheWeek.plusDays(i));
                pstmt.setDate(1, date);
                rs = pstmt.executeQuery();
                message += (date + "\n");

                if (!rs.next()) {
                    // 결과 집합에 레코드가 없는 경우
                    message += "값 없음\n";
                } else {
                    // 결과 집합에 레코드가 있는 경우
                    do {
                        // 레코드 처리
                        message += (rs.getString("item_number") + ". ");
                        message += (rs.getString("item_name") + "\n");
                    } while (rs.next());
                }
                message += "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close() 메서드에 대한 예외처리도 필요합니다.
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }

        return message;
    }
}